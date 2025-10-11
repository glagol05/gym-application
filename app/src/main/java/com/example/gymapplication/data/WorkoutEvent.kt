package com.example.gymapplication.data

sealed interface WorkoutEvent {

    object SaveWorkout: WorkoutEvent

    data class setWorkoutType(val workoutType: String): WorkoutEvent
    data class setWorkoutDate(val date: String): WorkoutEvent

    object ShowDialog: WorkoutEvent
    object HideDialog: WorkoutEvent

    data class SortWorkout(val SortType: SortType): WorkoutEvent
    data class DeleteWorkout(val workout: Workout): WorkoutEvent
}