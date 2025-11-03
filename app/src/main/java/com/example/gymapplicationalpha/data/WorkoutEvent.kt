package com.example.gymapplicationalpha.data

sealed interface WorkoutEvent {

    object saveWorkout: WorkoutEvent

    object deleteWorkout: WorkoutEvent

    data class AddWorkoutExerciseCrossRef(
        val workoutSession: Int,
        val exerciseName: String
    ): WorkoutEvent

    data class DeleteWorkoutExerciseCrossRef(
        val workoutSession: Int,
        val exerciseName: String
    ): WorkoutEvent

    data class SortWorkout(val SortType: SortType): WorkoutEvent



}