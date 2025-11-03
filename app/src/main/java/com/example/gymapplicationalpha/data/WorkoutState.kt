package com.example.gymapplicationalpha.data

data class WorkoutState(
    val workouts: List<Workout> = emptyList(),
    val date: String = "",
    val type: String = "",
    val description: String? = null,
    val isAddingWorkout: Boolean = false,
    val sortType: SortType = SortType.WORKOUT_DATE
)