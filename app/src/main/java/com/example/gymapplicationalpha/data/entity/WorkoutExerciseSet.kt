package com.example.gymapplicationalpha.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_exercise_sets")
data class WorkoutExerciseSet(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val workoutId: Int? = null,
    val exerciseName: String? = "",
    val setNumber: Int,
    val repNumber: Int,
    val weight: Float? = null
)