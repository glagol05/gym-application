package com.example.gymapplicationalpha.data.events

import com.example.gymapplicationalpha.data.SortType
import com.example.gymapplicationalpha.data.entity.WorkoutExerciseSet

sealed interface WorkoutExerciseSetEvent {

    object saveSet: WorkoutExerciseSetEvent

    data class deleteSet(val workoutExerciseSet: WorkoutExerciseSet): WorkoutExerciseSetEvent

    data class sortSet(val sortType: SortType): WorkoutExerciseSetEvent
}