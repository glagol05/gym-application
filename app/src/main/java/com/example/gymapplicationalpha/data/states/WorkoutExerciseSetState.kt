package com.example.gymapplicationalpha.data.states

import com.example.gymapplicationalpha.data.SortType
import com.example.gymapplicationalpha.data.entity.WorkoutExerciseSet

data class WorkoutExerciseSetState (
    val sets: List<WorkoutExerciseSet> = emptyList(),
    val setNumber: Int = 0,
    val repNumber: Int = 0,
    val weight: Float = 0f,
    val sortType: SortType = SortType.SET_NUMBER

)