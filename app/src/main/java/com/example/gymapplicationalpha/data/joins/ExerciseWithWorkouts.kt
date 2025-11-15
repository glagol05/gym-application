package com.example.gymapplicationalpha.data.joins

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.example.gymapplicationalpha.data.joins.WorkoutExerciseCrossRef
import com.example.gymapplicationalpha.data.entity.Exercise
import com.example.gymapplicationalpha.data.entity.Workout

data class ExercisesWithWorkouts (
    @Embedded val exercise: Exercise,
    @Relation(
        parentColumn = "exerciseId",
        entityColumn = "workoutSession",
        associateBy = Junction(WorkoutExerciseCrossRef::class)
    )

    val exercises: List<Workout>

)