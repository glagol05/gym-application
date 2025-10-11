package com.example.gymapplication.data

data class WorkoutState (
    val workouts: List<Workout> = emptyList(),
    val workoutType: String = "",
    val date: String = "",
    val isAddingWorkout: Boolean = false,
    val sortType: SortType = SortType.DATE

)