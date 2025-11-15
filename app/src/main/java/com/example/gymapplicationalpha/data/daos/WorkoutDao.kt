package com.example.gymapplicationalpha.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.gymapplicationalpha.data.entity.Exercise
import com.example.gymapplicationalpha.data.entity.Workout
import com.example.gymapplicationalpha.data.joins.WorkoutExerciseCrossRef
import com.example.gymapplicationalpha.data.joins.WorkoutWithExercises
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

    @Query("SELECT * FROM workouts WHERE workoutSession = :id")
    fun getWorkoutById(id: Int): Flow<Workout?>

    @Query("SELECT * FROM workouts ORDER BY workoutType ASC")
    fun getWorkoutsByType(): Flow<List<Workout>>

    @Query("SELECT * FROM workouts ORDER BY date ASC")
    fun getWorkoutsByDate(): Flow<List<Workout>>

    @Query("SELECT * FROM workouts ORDER BY workoutSession ASC")
    fun getWorkoutsById(): Flow<List<Workout>>

    @Transaction
    @Query("SELECT * FROM workouts WHERE workoutSession = :workoutSession")
    fun getWorkoutWithExercises(workoutSession: Int): Flow<WorkoutWithExercises>

    @Transaction
    @Query("SELECT * FROM workouts WHERE workoutSession IN (SELECT workoutSession FROM workoutexercisecrossref WHERE exerciseId = :exerciseId)")
    suspend fun getWorkoutsForExercise(exerciseId: Int): List<Workout>
}