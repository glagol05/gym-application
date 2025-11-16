package com.example.gymapplicationalpha.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import com.example.gymapplicationalpha.components.AddExerciseRow
import com.example.gymapplicationalpha.data.AppDatabase
import com.example.gymapplicationalpha.data.daos.WorkoutDao
import com.example.gymapplicationalpha.data.entity.Workout
import com.example.gymapplicationalpha.data.viewmodels.ExerciseViewModel
import com.example.gymapplicationalpha.data.viewmodels.WorkoutViewModel
import com.google.crypto.tink.shaded.protobuf.LazyStringArrayList.emptyList
import kotlin.collections.emptyList
import com.example.gymapplicationalpha.data.entity.Exercise
import com.example.gymapplicationalpha.data.events.ExerciseEvent

@Composable
fun WorkoutDetails(
    navController: NavController,
    workoutSession: Int,
) {

    val context = LocalContext.current
    val appDatabase = AppDatabase.getInstance(context)

    val workoutDao = appDatabase.workoutDao
    val exerciseDao = appDatabase.exerciseDao

    val workoutViewModel: WorkoutViewModel = remember {
        WorkoutViewModel(workoutDao = workoutDao)
    }

    val exerciseViewModel: ExerciseViewModel = remember {
        ExerciseViewModel(exerciseDao = exerciseDao)
    }

    val workoutWithExercises by workoutViewModel.getExercisesForWorkout(workoutSession)
        .collectAsState(initial = null)

    val workout = workoutWithExercises?.workout
    val exercisesForWorkout: List<Exercise> = (workoutWithExercises?.exercises ?: emptyList()) as List<Exercise>

    Column(modifier = Modifier.padding(top = 64.dp)) {
        AddExerciseRow(onAddExerciseClicked = {
            navController.navigate(Screen.AddExerciseScreen.passSession(workoutSession))
        })
        Text(text = workout?.workoutType ?: "")
        Text(text = workout?.date ?: "")

        exercisesForWorkout.forEach { exercise ->
            Text(
                text = exercise.exerciseName,
                modifier = Modifier.padding(vertical = 4.dp)
            )
            Button(
                onClick = {
                    exerciseViewModel.onEvent(ExerciseEvent.RemoveExerciseFromWorkout(workoutSession, exercise.exerciseId))
                }
            ) {
                Text(text = "Remove exercise")
            }
        }
    }
}