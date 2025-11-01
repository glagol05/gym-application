package com.example.gymapplicationalpha.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {

    @Upsert
    suspend fun upsertWorkout(workout: Workout)

    @Delete
    suspend fun deleteWorkout(workout: Workout)

    @Upsert
    suspend fun upsertWorkoutExerciseCrossRef(crossRef: WorkoutExerciseCrossRef)

    @Delete
    suspend fun deleteWorkoutExerciseCrossRef(crossRef: WorkoutExerciseCrossRef)

    @Query("SELECT * FROM workouts ORDER BY workoutType ASC")
    fun getWorkoutsByType(): Flow<List<Workout>>

    @Query("SELECT * FROM workouts ORDER BY date ASC")
    fun getWorkoutsByDate(): Flow<List<Workout>>

    @Transaction
    @Query("SELECT * FROM exercises WHERE exerciseName IN (SELECT exerciseName FROM WorkoutExerciseCrossRef WHERE workoutSession = :workoutSession)")
    suspend fun getExercisesForWorkout(workoutSession: Int): List<Exercise>

    @Transaction
    @Query("SELECT * FROM workouts WHERE workoutSession IN (SELECT workoutSession FROM workoutexercisecrossref WHERE exerciseName = :exerciseName)")
    suspend fun getWorkoutsForExercise(exerciseName: String): List<Workout>

}