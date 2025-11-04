package com.example.gymapplicationalpha.data.joins

@Entity(primaryKeys = ["workoutSession", "exerciseName"])
data class WorkoutExerciseCrossRef (
    val workoutSession: Int,
    val exerciseName: String
)