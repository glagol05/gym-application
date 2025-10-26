package com.example.gymapplicationalpha

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.gymapplicationalpha.pages.AddWorkout
import com.example.gymapplicationalpha.pages.Calendar
import com.example.gymapplicationalpha.pages.MainScreen
import com.example.gymapplicationalpha.pages.Settings
import com.example.gymapplicationalpha.pages.Statistics

@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            MainScreen(navController = navController)
        }
        composable(route = Screen.AddWorkout.route) {
            AddWorkout(navController = navController)
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