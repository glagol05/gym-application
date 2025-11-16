package com.example.gymapplicationalpha.data.joins

import androidx.room.Entity
import androidx.room.Index

@Entity(primaryKeys = ["workoutSession", "exerciseId"],
        indices = [Index(value = ["exerciseId"])])
data class WorkoutExerciseCrossRef (
    val workoutSession: Int,
    val exerciseId: Int
)