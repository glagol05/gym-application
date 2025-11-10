package com.example.gymapplicationalpha.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymapplicationalpha.Screen
import com.example.gymapplicationalpha.components.AddExerciseRow
import com.example.gymapplicationalpha.data.AppDatabase
import com.example.gymapplicationalpha.data.entity.Exercise
import com.example.gymapplicationalpha.data.events.ExerciseEvent
import com.example.gymapplicationalpha.data.events.WorkoutEvent
import com.example.gymapplicationalpha.data.viewmodels.ExerciseViewModel
import com.example.gymapplicationalpha.data.viewmodels.WorkoutViewModel

@Composable
fun AddWorkout(
    navController: NavController,
) {
    val context = LocalContext.current
    val appDatabase = AppDatabase.getInstance(context)

    val workoutDao = appDatabase.workoutDao
    val exerciseDao = appDatabase.exerciseDao

    val workoutViewModel: WorkoutViewModel = remember{
        WorkoutViewModel(workoutDao = workoutDao)
    }

    val exerciseViewModel: ExerciseViewModel = remember{
        ExerciseViewModel(exerciseDao = exerciseDao)
    }

    val state by exerciseViewModel.state.collectAsState()

    val exerciseChoices = ArrayList<Exercise>()

    Column(modifier = Modifier.padding(16.dp)) {
        AddExerciseRow(onAddExerciseClicked = { navController.navigate(Screen.AddExerciseScreen.route) })
        TextField(
            value = state.name,
            onValueChange = {
                workoutViewModel.onEvent(WorkoutEvent.setWorkoutType(it))
                },
            label = { Text("Exercise name") }
        )
        TextField(
            value = state.type,
            onValueChange = {
                exerciseViewModel.onEvent(ExerciseEvent.setExerciseType(it))
                //exerciseViewModel.onEvent(ExerciseEvent.setExerciseImage(R.drawable.squat))
                },
            label = { Text("Exercise type") }
        )

        //exerciseViewModel.onEvent(ExerciseEvent.setExerciseImage(R.drawable.bench_press))

        Button(onClick = {
            exerciseViewModel.onEvent(ExerciseEvent.SaveExercise)
        }) {
            Text("Save exercise")
        }

    }
}