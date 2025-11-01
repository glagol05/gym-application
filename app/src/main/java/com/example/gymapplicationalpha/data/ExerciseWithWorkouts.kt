package com.example.gymapplicationalpha.data

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class ExercisesWithWorkouts (
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "exerciseName",
        entityColumn = "workoutSession",
        associateBy = Junction(WorkoutExerciseCrossRef::class)
    )

    val exercises: List<Workout>

)