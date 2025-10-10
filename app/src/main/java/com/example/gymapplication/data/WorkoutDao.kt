package com.example.gymapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao{

    @Upsert
    suspend fun upsertWorkout(workout: Workout)

    @Delete
    suspend fun deleteContact(workoutDao: Workout)

    @Query("SELECT * FROM workouts ORDER BY date ASC")
    fun getWorkoutsByDate(): Flow<List<Workout>>

    @Query("SELECT * FROM workouts ORDER BY workoutType ASC")
    fun getWorkoutsByType(): Flow<List<Workout>>
}