package com.example.gymapplicationalpha.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercises")
data class Exercise (
    @PrimaryKey(autoGenerate = true)
    val exerciseId: Int = 0,
    @ColumnInfo(name = "exerciseName")
    val exerciseName: String,
    @ColumnInfo(name = "exerciseType")
    val exerciseType: String,
    @ColumnInfo(name = "imageName")
    val imageName: String
)