package com.example.gymapplication.data

sealed interface WorkoutEvent {

    object SaveWorkout: WorkoutEvent

    data class setWorkoutType(val workoutType: String): WorkoutEvent
    data class setWorkoutDate(val date: String): WorkoutEvent
}