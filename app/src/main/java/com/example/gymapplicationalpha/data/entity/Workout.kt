package com.example.gymapplicationalpha.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workouts")
data class Workout (
    @PrimaryKey(autoGenerate = true)
    val workoutSession: Int = 0,
    val date: String,
    val workoutType: String,
    val description: String?
)