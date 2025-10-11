package com.example.gymapplication.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gymapplication.data.WorkoutEvent
import com.example.gymapplication.data.WorkoutState
import com.example.gymapplication.data.WorkoutViewModel


@Composable
fun AddWorkoutAdvanced(
    selectedWorkout: String,
    selectedDate: String,
    viewModel: WorkoutViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(WorkoutEvent.setWorkoutType(selectedWorkout))
                viewModel.onEvent(WorkoutEvent.setWorkoutDate(selectedDate))
                viewModel.onEvent(WorkoutEvent.SaveWorkout)
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Add workout")
            }
        }
    ) { padding ->
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Text(text = "Workout: $selectedWorkout\nDate: $selectedDate")
            }
            items(state.workouts) { workout ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = workout.workoutType, fontSize = 20.sp)
                        Text(text = workout.date, fontSize = 20.sp)
                    }
                    IconButton(onClick = { viewModel.onEvent(WorkoutEvent.DeleteWorkout(workout)) }) {
                        Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete workout")
                    }
                }
            }
        }
    }
}
