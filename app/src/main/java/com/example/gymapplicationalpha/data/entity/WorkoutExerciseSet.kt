package com.example.gymapplicationalpha.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_exercise_sets", primaryKeys = ["workoutId", "exerciseId", "setNumber"])
data class WorkoutExerciseSet(
    val workoutId: Int,
    val exerciseId: Int,
    val setNumber: Int,
    val repNumber: Int,
    val weight: Float? = null
)