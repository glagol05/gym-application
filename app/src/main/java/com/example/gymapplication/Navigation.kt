package com.example.gymapplication

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
            AddWorkoutAdvanced(
                selectedWorkout = entry.arguments?.getString("selectedWorkout"),
                selectedDate = entry.arguments?.getString("selectedDate")
            )
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