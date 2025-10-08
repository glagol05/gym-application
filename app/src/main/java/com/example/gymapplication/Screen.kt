package com.example.gymapplication

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object AddWorkout : Screen("add_workout")
    object AddWorkoutAdvanced : Screen("add_workout_advanced")
    object WorkoutStatistics : Screen("workout_statistics")
    object Calendar : Screen("calendar")
    object Template : Screen("template")
    object Settings : Screen("settings")

    fun withArgs(vararg args: String) : String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("/$arg")
            }
        }
    }
}