package com.example.gymapplicationalpha.data

import androidx.room.Entity
import java.util.Date

@Entity(primaryKeys = ["workoutSession", "exerciseName"])
data class WorkoutExerciseCrossRef (
    val workoutSession: Int,
    val exerciseName: String
)