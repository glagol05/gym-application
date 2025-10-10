package com.example.gymapplication.data
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "workouts")
data class Workout(
    @PrimaryKey(true)
    val id: Int = 0,
    val workoutType: String,
    val date: String
)