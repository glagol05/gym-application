package com.example.gymapplication

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.gymapplication.pages.AddWorkout
import com.example.gymapplication.pages.AddWorkoutAdvanced
import com.example.gymapplication.pages.Calendar
import com.example.gymapplication.pages.MainScreen
import com.example.gymapplication.pages.Settings
import com.example.gymapplication.pages.Template
import com.example.gymapplication.pages.WorkoutStatistics

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
        composable(route = Screen.AddWorkoutAdvanced.route) {
            AddWorkoutAdvanced()
        }
        composable(route = Screen.WorkoutStatistics.route) {
            WorkoutStatistics()
        }
        composable(route = Screen.Calendar.route) {
            Calendar()
        }
        composable(route = Screen.Template.route) {
            Template()
        }
        composable(route = Screen.Settings.route) {
            Settings()
        }
    }
}

//@Composable
//fun MainScreen(navController: NavController) {
//    var text by remember {
//        mutableStateOf("")
//    }
//
//    Column(
//        verticalArrangement = Arrangement.Center,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 50.dp)
//    ) {
//        TextField(value = text, onValueChange = {
//            text = it
//        },
//            modifier = Modifier.fillMaxWidth()
//        )
//        Spacer(modifier = Modifier.height(8.dp))
//        Button(
//            onClick = {
//                navController.navigate(Screen.AddWorkout.withArgs(text))
//        },
//            modifier = Modifier.align(Alignment.End)
//            ) {
//            Text(text = "To another screen")
//        }
//    }
//}

//@Composable
//fun AddWorkout(name: String?) {
//    Box(
//        contentAlignment = Alignment.Center,
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Text(text = "Hello $name")
//    }
//}

//fun Navigation() {
//    val navController = rememberNavController()
//    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
//        composable(route = Screen.MainScreen.route) {
//            MainScreen(navController = navController)
//        }
//        composable(
//            route = Screen.AddWorkout.route + "/{name}",
//            arguments = listOf(
//                navArgument("name") {
//                    type = NavType.StringType
//                    defaultValue = "Dude"
//                    nullable = true
//                }
//            )
//        ) { entry ->
//            AddWorkout()
//        }
//    }
//}