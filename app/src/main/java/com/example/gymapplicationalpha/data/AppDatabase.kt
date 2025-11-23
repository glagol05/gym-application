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
    version = 8
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


        val MIGRATION_7_8 = object : Migration(7, 8) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // 1. Add the new column with a default value (e.g., 0 or current timestamp)
                database.execSQL("ALTER TABLE WorkoutExerciseCrossRef ADD COLUMN addedOrder INTEGER NOT NULL DEFAULT 0")
            }
        }


        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gymapp_db"
                )
                    .addMigrations(MIGRATION_7_8)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
