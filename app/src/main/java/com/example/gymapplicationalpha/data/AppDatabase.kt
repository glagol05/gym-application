package com.example.gymapplicationalpha.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.synchronized

@Database(
    entities = [
        Workout::class,
        Exercise::class,
        WorkoutExerciseCrossRef::class
    ],
    version = 1
)

abstract class AppDatabase : RoomDatabase() {

    abstract val workoutDao: WorkoutDao
    abstract val exerciseDao: ExerciseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
    }

    fun getInstance(context: Context): AppDatabase {
        synchronized(this) {
            return INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "gymapp_db"
            ).build().also {
                INSTANCE = it
            }
        }
    }
}