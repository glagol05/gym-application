package com.example.gymapplicationalpha.pages

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gymapplicationalpha.R
import com.example.gymapplicationalpha.Screen
import com.example.gymapplicationalpha.components.AddExerciseRow
import com.example.gymapplicationalpha.data.AppDatabase
import com.example.gymapplicationalpha.data.events.ExerciseEvent
import com.example.gymapplicationalpha.data.viewmodels.ExerciseViewModel
import com.example.gymapplicationalpha.data.viewmodels.WorkoutViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp

@Composable
fun AddExercise(
    navController: NavController,
) {
    val context = LocalContext.current
    val appDatabase = AppDatabase.getInstance(context)

    val exerciseDao = appDatabase.exerciseDao

    val exerciseViewModel: ExerciseViewModel = remember {
        ExerciseViewModel(exerciseDao = exerciseDao)
    }

    val state by exerciseViewModel.state.collectAsState()

    Column(modifier = Modifier.padding(top = 32.dp, start = 16.dp, end = 16.dp)) {
        // Exercise Name Input
        TextField(
            value = state.name,
            onValueChange = {
                exerciseViewModel.onEvent(ExerciseEvent.setExerciseName(it))
            },
            label = { Text("Exercise name") }
        )

        // Exercise Type Input
        TextField(
            value = state.type,
            onValueChange = {
                exerciseViewModel.onEvent(ExerciseEvent.setExerciseType(it))
            },
            label = { Text("Exercise type") }
        )

        // Save Button
        Button(onClick = {
            exerciseViewModel.onEvent(ExerciseEvent.SaveExercise)
        }) {
            Text("Save exercise")
        }

        if (state.exercises.isNotEmpty()) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(state.exercises) { exercise ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Show exercise image if valid
                        if (exercise.imageRes != 0) {
                            Image(
                                painter = painterResource(id = exercise.imageRes),
                                contentDescription = exercise.exerciseName,
                                modifier = Modifier.size(64.dp)
                                    .clickable {
                                        println("Exercise clicked: ${exercise.exerciseName}")
                                    }
                            )
                        } else {
                            Image(
                                painter = painterResource(id = R.drawable.exercise),
                                contentDescription = exercise.exerciseName,
                                modifier = Modifier.size(64.dp)
                                    .clickable {
                                        println("Alt image loaded")
                                    }
                            )
                        }
                        Text(
                            text = exercise.exerciseName,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        } else {
            Text(
                text = "No exercises available",
                fontSize = 18.sp,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}