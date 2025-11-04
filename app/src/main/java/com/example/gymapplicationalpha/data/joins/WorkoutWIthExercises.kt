package com.example.gymapplicationalpha.data.joins

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.gymapplicationalpha.data.entity.Exercise
import com.example.gymapplicationalpha.data.entity.Workout

data class WorkoutWIthExercises (
    @Embedded val workout: Workout,
    @Relation(
        parentColumn = "workoutSession",
        entityColumn = "exerciseName",
        associateBy = Junction(WorkoutExerciseCrossRef::class)
    )

    val workouts: List<Exercise>

)