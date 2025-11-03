package com.example.gymapplicationalpha.data

data class ExerciseState(
    val exercises: List<Exercise> = emptyList(),
    val name: String = "",
    val type: String = "",
    val imageRes: Int = 0,
    val isAddingExercise: Boolean = false,
    val sortType: SortType = SortType.EXERCISE_NAME
)