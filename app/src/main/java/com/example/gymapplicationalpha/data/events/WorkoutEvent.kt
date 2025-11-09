package com.example.gymapplicationalpha.data.events

import com.example.gymapplicationalpha.data.SortType
import com.example.gymapplicationalpha.data.entity.Workout

sealed interface WorkoutEvent {

    object SaveWorkout: WorkoutEvent

    data class DeleteWorkout(val workout: Workout): WorkoutEvent

    data class setWorkoutDate(val workoutDate: String): WorkoutEvent
    data class setWorkoutType(val workoutType: String): WorkoutEvent
    data class setWorkoutDescription(val workoutDescription: String): WorkoutEvent

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