package com.example.gymapplicationalpha.data.joins

import androidx.room.Entity

@Entity(primaryKeys = ["workoutSession", "exerciseName"])
data class WorkoutExerciseCrossRef (
    val workoutSession: Int,
    val exerciseName: String
)