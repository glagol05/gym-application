package com.example.gymapplicationalpha.data.joins

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.gymapplicationalpha.data.entity.Exercise
import com.example.gymapplicationalpha.data.entity.Workout
import com.example.gymapplicationalpha.data.entity.WorkoutExerciseSet

data class WorkoutWithSets(
    @Embedded val workout: Workout,
    @Relation(
        parentColumn = "workoutSession",
        entityColumn = "exerciseId",
        associateBy = Junction(WorkoutExerciseCrossRef::class)
    )
    val exercises: List<Exercise>,
    @Relation(
        parentColumn = "workoutSession",
        entityColumn = "workoutId"
    )
    val sets: List<WorkoutExerciseSet>
)
