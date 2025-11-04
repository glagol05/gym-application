package com.example.gymapplicationalpha.pages

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gymapplicationalpha.components.AddExerciseRow
import com.example.gymapplicationalpha.data.AppDatabase
import com.example.gymapplicationalpha.data.ExerciseViewModel
import com.example.gymapplicationalpha.data.WorkoutDao_Impl
import com.example.gymapplicationalpha.data.WorkoutViewModel

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

    Column(modifier = Modifier.padding(16.dp)) {
        AddExerciseRow {

        }
    }

}