package com.example.gymapplicationalpha.data.states

import com.example.gymapplicationalpha.data.SortType
import com.example.gymapplicationalpha.data.entity.Workout

data class WorkoutState(
    val workouts: List<Workout> = emptyList(),
    val date: String = "",
    val type: String = "",
    val description: String? = null,
    val isAddingWorkout: Boolean = false,
    val sortType: SortType = SortType.WORKOUT_DATE
)