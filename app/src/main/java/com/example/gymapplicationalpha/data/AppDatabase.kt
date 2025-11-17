package com.example.gymapplicationalpha.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.gymapplicationalpha.data.daos.ExerciseDao
import com.example.gymapplicationalpha.data.daos.WorkoutDao
import com.example.gymapplicationalpha.data.daos.WorkoutExerciseSetDao
import com.example.gymapplicationalpha.data.entity.Exercise
import com.example.gymapplicationalpha.data.entity.Workout
import com.example.gymapplicationalpha.data.entity.WorkoutExerciseSet
import com.example.gymapplicationalpha.data.joins.WorkoutExerciseCrossRef

@Database(
    entities = [
        Workout::class,
        Exercise::class,
        WorkoutExerciseSet::class,
        WorkoutExerciseCrossRef::class
    ],
    version = 7
)
abstract class AppDatabase : RoomDatabase() {

    abstract val workoutDao: WorkoutDao
    abstract val exerciseDao: ExerciseDao
    abstract val workoutExerciseSetDao: WorkoutExerciseSetDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val MIGRATION_4_5 = object : Migration(6, 7) {
            override fun migrate(database: SupportSQLiteDatabase) {

            }
        }


        val MIGRATION_5_6 = object : Migration(6, 7) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // 1. Rename the old table
                database.execSQL("ALTER TABLE workout_exercise_sets RENAME TO workout_exercise_sets_old")

                // 2. Create the new table with composite primary key
                database.execSQL("""
            CREATE TABLE IF NOT EXISTS workout_exercise_sets (
                workoutId INTEGER NOT NULL,
                exerciseId INTEGER NOT NULL,
                setNumber INTEGER NOT NULL,
                repNumber INTEGER NOT NULL,
                weight REAL,
                PRIMARY KEY(workoutId, exerciseId, setNumber)
            )
        """)

                // 3. Copy data from old table
                database.execSQL("""
            INSERT INTO workout_exercise_sets (workoutId, exerciseId, setNumber, repNumber, weight)
            SELECT workoutId, exerciseId, setNumber, repNumber, weight
            FROM workout_exercise_sets_old
        """)

                // 4. Drop the old table
                database.execSQL("DROP TABLE workout_exercise_sets_old")

                // 5. Migrate WorkoutExerciseCrossRef as before
                database.execSQL("ALTER TABLE WorkoutExerciseCrossRef RENAME TO WorkoutExerciseCrossRef_old")
                database.execSQL("""
            CREATE TABLE IF NOT EXISTS WorkoutExerciseCrossRef (
                workoutSession INTEGER NOT NULL,
                exerciseId INTEGER NOT NULL,
                PRIMARY KEY(workoutSession, exerciseId)
            )
        """)
                database.execSQL("CREATE INDEX index_WorkoutExerciseCrossRef_exerciseId ON WorkoutExerciseCrossRef(exerciseId)")
                database.execSQL("""
            INSERT INTO WorkoutExerciseCrossRef (workoutSession, exerciseId)
            SELECT workoutSession, exerciseId FROM WorkoutExerciseCrossRef_old
        """)
                database.execSQL("DROP TABLE WorkoutExerciseCrossRef_old")
            }
        }


        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gymapp_db"
                )
                    .addMigrations(MIGRATION_5_6)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
