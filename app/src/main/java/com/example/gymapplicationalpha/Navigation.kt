package com.example.gymapplicationalpha

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.gymapplicationalpha.data.viewmodels.ExerciseViewModel
import com.example.gymapplicationalpha.data.viewmodels.WorkoutViewModel
import com.example.gymapplicationalpha.pages.AddExercise
import com.example.gymapplicationalpha.pages.AddExerciseScreen
import com.example.gymapplicationalpha.pages.AddWorkout
import com.example.gymapplicationalpha.pages.Calendar
import com.example.gymapplicationalpha.pages.MainScreen
import com.example.gymapplicationalpha.pages.Settings
import com.example.gymapplicationalpha.pages.Statistics
import com.example.gymapplicationalpha.pages.Workout
import com.example.gymapplicationalpha.pages.WorkoutDetails

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screen.Workout.route) {
            Workout(navController = navController)
        }
        composable(
            route = Screen.WorkoutDetails.route,
            arguments = listOf(navArgument("workoutSession") { type = NavType.IntType })
        ) { backStackEntry ->

            val workoutSession = backStackEntry.arguments?.getInt("workoutSession") ?: 0

            WorkoutDetails(
                navController = navController,
                workoutSession = workoutSession
            )
        }
        composable(route = Screen.AddWorkout.route) {
            AddWorkout(navController = navController)
        }
        composable(route = Screen.AddExerciseScreen.route,
            arguments = listOf(navArgument("workoutSession") { type = NavType.IntType })
        ) { backStackEntry ->
            
            val workoutSession = backStackEntry.arguments?.getInt("workoutSession") ?: 0
            
            AddExerciseScreen(
                navController = navController,
                workoutSession = workoutSession,
                onExerciseSelected = { exerciseName ->
                    println("Exercise $exerciseName added to workout $workoutSession")
                }
            )
        }
        composable(route = Screen.AddExercise.route) {
            AddExercise(navController = navController)
        }
        composable(route = Screen.Statistics.route) {
            Statistics(navController = navController)
        }
        composable(route = Screen.Calendar.route) {
            Calendar(navController = navController)
        }
        composable(route = Screen.Settings.route) {
            Settings(navController = navController)
        }
    }
}