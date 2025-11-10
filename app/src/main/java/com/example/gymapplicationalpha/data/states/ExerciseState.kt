package com.example.gymapplicationalpha.data.states

import com.example.gymapplicationalpha.data.SortType
import com.example.gymapplicationalpha.data.entity.Exercise

data class ExerciseState(
    val exercises: List<Exercise> = emptyList(),
    val name: String = "",
    val type: String = "",
    val imageName: String = "",
    val isAddingExercise: Boolean = false,
    val sortType: SortType = SortType.EXERCISE_NAME
)