package com.example.gymapplicationalpha.pages

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymapplicationalpha.Screen
import com.example.gymapplicationalpha.components.WorkoutCard
import com.example.gymapplicationalpha.data.AppDatabase
import com.example.gymapplicationalpha.data.viewmodels.WorkoutViewModel

@Composable
fun Workout(navController: NavController) {

    val context = LocalContext.current
    val appDatabase = AppDatabase.getInstance(context)

    val workoutDao = appDatabase.workoutDao

    val workoutViewModel : WorkoutViewModel = remember {
        WorkoutViewModel(workoutDao = workoutDao)
    }

    val state by workoutViewModel.state.collectAsState()

    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Button(
            onClick = { navController.navigate(Screen.AddWorkout.route) },
            modifier = Modifier
                .padding(top = 32.dp)
                .fillMaxWidth()
        ) {
            Text("Add workout session")
        }

        Spacer(modifier = Modifier.height(16.dp))
        WorkoutCard(
            "Test",
            "test",
            onClick = {}
        )

        WorkoutCard(
            "Test",
            "test",
            onClick = {}
        )

        WorkoutCard(
            "Test",
            "test",
            onClick = {}
        )

        WorkoutCard(
            "Test4444",
            "test",
            onClick = {}
        )

        if(state.workouts.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 96.dp)
            ) {

            }
        } else {
            Text("You have no workout sessions registered")
        }
    }
}