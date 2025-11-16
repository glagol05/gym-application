package com.example.gymapplicationalpha.data.daos

import com.example.gymapplicationalpha.data.joins.WorkoutWithSets
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.gymapplicationalpha.data.entity.WorkoutExerciseSet
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutExerciseSetDao {

    @Upsert
    suspend fun upsertSet (workoutExerciseSet: WorkoutExerciseSet)

    @Delete
    suspend fun deleteSet (workoutExerciseSet: WorkoutExerciseSet)

    @Transaction
    @Query("SELECT * FROM workouts WHERE workoutSession = :workoutId")
    fun getWorkoutWithSets(workoutId: Int): List<WorkoutWithSets>

    @Query("SELECT * FROM workout_exercise_sets WHERE workoutId = :workoutId AND exerciseId = :exerciseId ORDER BY setNumber ASC")
    fun getSetsForExerciseInWorkout(workoutId: Int, exerciseId: Int): Flow<List<WorkoutExerciseSet>>
}