package com.example.gymapplicationalpha.data.joins

import androidx.room.Entity

@Entity(primaryKeys = ["workoutSession", "exerciseId"])
data class WorkoutExerciseCrossRef (
    val workoutSession: Int,
    val exerciseId: Int
)