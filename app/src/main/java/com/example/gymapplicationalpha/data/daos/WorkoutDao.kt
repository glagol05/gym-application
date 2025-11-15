package com.example.gymapplicationalpha.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.gymapplicationalpha.data.entity.Exercise
import com.example.gymapplicationalpha.data.entity.Workout
import com.example.gymapplicationalpha.data.joins.WorkoutExerciseCrossRef
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

    @Query("SELECT * FROM workouts ORDER BY workoutSession ASC")
    fun getWorkoutsById(): Flow<List<Workout>>

    @Transaction
    @Query("SELECT * FROM exercises WHERE exerciseName IN (SELECT exerciseName FROM WorkoutExerciseCrossRef WHERE workoutSession = :workoutSession)")
    fun getExercisesForWorkout(workoutSession: Int): Flow<List<Exercise>>

    @Transaction
    @Query("SELECT * FROM workouts WHERE workoutSession IN (SELECT workoutSession FROM workoutexercisecrossref WHERE exerciseName = :exerciseName)")
    suspend fun getWorkoutsForExercise(exerciseName: String): List<Workout>

}