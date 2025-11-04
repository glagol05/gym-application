package com.example.gymapplicationalpha.data

sealed interface ExerciseEvent {

    object SaveExercise: ExerciseEvent

    data class setExerciseName(val exerciseName: String): ExerciseEvent
    data class setExerciseType(val exerciseType: String): ExerciseEvent
    data class setExerciseImage(val imageRes: Int): ExerciseEvent

    data class AddExerciseToWorkout(
        val workoutSession: Int,
        val exerciseName: String
    ) : ExerciseEvent

    data class RemoveExerciseFromWorkout(
        val workoutSession: Int,
        val exerciseName: String
    ) : ExerciseEvent

    data class SortExercise(val SortType: SortType): ExerciseEvent

    data class DeleteExercise(val exercise: Exercise): ExerciseEvent

}