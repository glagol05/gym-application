package com.example.gymapplicationalpha.data.events

import com.example.gymapplicationalpha.data.SortType
import com.example.gymapplicationalpha.data.entity.WorkoutExerciseSet

sealed interface WorkoutExerciseSetEvent {

    data class saveSet(
        val workoutSession: Int,
        val exerciseId: Int,
        val setNumber: Int,
        val repNumber: Int,
        val weight: String
    ) : WorkoutExerciseSetEvent

    data class deleteSet(val workoutExerciseSet: WorkoutExerciseSet): WorkoutExerciseSetEvent

    data class sortSet(val sortType: SortType): WorkoutExerciseSetEvent
}