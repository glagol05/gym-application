package com.example.gymapplicationalpha.data.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.gymapplicationalpha.data.entity.WorkoutExerciseSet

@Dao
interface WorkoutExerciseSetDao {

    @Query("SELECT * FROM workout_exercise_sets WHERE workoutId = :workoutId AND exerciseName = :exerciseName")
    suspend fun getSetsForExerciseInWorkout(workoutId: Int, exerciseName: String): List<WorkoutExerciseSet>
}