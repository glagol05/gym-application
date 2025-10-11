package com.example.gymapplication

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.gymapplication.data.WorkoutDatabase
import com.example.gymapplication.data.WorkoutEvent
import com.example.gymapplication.data.WorkoutViewModel
import com.example.gymapplication.pages.AddWorkout
import com.example.gymapplication.pages.AddWorkoutAdvanced
import com.example.gymapplication.pages.Calendar
import com.example.gymapplication.pages.MainScreen
import com.example.gymapplication.pages.Settings
import com.example.gymapplication.pages.Template
import com.example.gymapplication.pages.WorkoutStatistics

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun Navigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    // Get DAO instance
    val dao = WorkoutDatabase.getInstance(context).dao

    // Create ViewModel with factory inline
    val workoutViewModel: WorkoutViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(WorkoutViewModel::class.java)) {
                    return WorkoutViewModel(dao) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    )

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screen.AddWorkout.route) {
            AddWorkout(navController = navController)
        }
        composable(
            route = Screen.AddWorkoutAdvanced.route + "/{selectedWorkout}/{selectedDate}",
            arguments = listOf(
                navArgument("selectedWorkout") {
                    type = NavType.StringType
                    defaultValue = "None"
                    nullable = true
                },
                navArgument("selectedDate") {
                    type = NavType.StringType
                    defaultValue = "No date selected"
                }
            )
        ) { entry ->
            val selectedWorkout = entry.arguments?.getString("selectedWorkout") ?: "None"
            val selectedDate = entry.arguments?.getString("selectedDate") ?: "No date selected"

            AddWorkoutAdvanced(
                selectedWorkout = selectedWorkout,
                selectedDate = selectedDate,
                viewModel = workoutViewModel
            )
        }
    }
}
