package com.example.gymapplicationalpha.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout (
    @PrimaryKey(autoGenerate = false)
    val workoutSession: Int,
    val date: String,
    val workoutType: String,
    val description: String
)