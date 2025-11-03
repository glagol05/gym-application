package com.example.gymapplicationalpha.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class WorkoutWIthExercises (
    @Embedded val workout: Workout,
    @Relation(
        parentColumn = "workoutSession",
        entityColumn = "exerciseName",
        associateBy = Junction(WorkoutExerciseCrossRef::class)
    )

    val workouts: List<Exercise>

)