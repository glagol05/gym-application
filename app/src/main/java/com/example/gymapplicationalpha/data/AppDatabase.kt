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
import kotlin.synchronized

@Database(
    entities = [
        Workout::class,
        Exercise::class,
        WorkoutExerciseSet::class,
        WorkoutExerciseCrossRef::class
    ],
    version = 2
)

abstract class AppDatabase : RoomDatabase() {

    abstract val workoutDao: WorkoutDao
    abstract val exerciseDao: ExerciseDao
    abstract val workoutExerciseSetDao: WorkoutExerciseSetDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Create the new table for WorkoutExerciseSet
                database.execSQL("""
            CREATE TABLE IF NOT EXISTS `workout_exercise_sets` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, 
                `workoutId` INTEGER, 
                `exerciseName` TEXT, 
                `setNumber` INTEGER NOT NULL, 
                `repNumber` INTEGER NOT NULL, 
                `weight` REAL)
        """)
            }
        }

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "gymapp_db"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}