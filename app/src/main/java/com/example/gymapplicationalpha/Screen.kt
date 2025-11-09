package com.example.gymapplicationalpha

sealed class Screen(val route: String) {

    object MainScreen : Screen("main_screen")
    object Workout : Screen("workout")
    object AddWorkout : Screen("add_workout")
    object AddExerciseScreen : Screen("add_exercise_screen")
    object AddExercise : Screen("add_exercise")
    object Statistics : Screen("statistics")
    object Calendar : Screen("calendar")
    object Settings : Screen("settings")

    fun withArgs(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append{"/$arg"}
            }
        }
    }
}