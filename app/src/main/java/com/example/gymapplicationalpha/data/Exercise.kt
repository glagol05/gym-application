package com.example.gymapplicationalpha.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "exercises")
data class Exercise (
    @PrimaryKey(autoGenerate = false)
    val exerciseName: String,
    val exerciseType: String,
    val imageRes: Int
)