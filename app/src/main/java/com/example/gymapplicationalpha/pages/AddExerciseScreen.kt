package com.example.gymapplicationalpha.pages

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.gymapplicationalpha.R
import com.example.gymapplicationalpha.data.AppDatabase
import com.example.gymapplicationalpha.data.events.WorkoutEvent
import com.example.gymapplicationalpha.data.viewmodels.ExerciseViewModel
import com.example.gymapplicationalpha.data.viewmodels.WorkoutViewModel

@SuppressLint("LocalContextResourcesRead", "DiscouragedApi", "RememberReturnType")
@Composable
fun AddExerciseScreen(
    navController: NavController,
    workoutSession: Int,
    onExerciseSelected: (exerciseName: String) -> Unit
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

    LazyColumn(
        contentPadding = PaddingValues(
            bottom = 50.dp
        )
    ) {
        items(state.exercises) { exercise ->
            val imageResId = remember(exercise.imageName) {
                context.resources.getIdentifier(exercise.imageName, "drawable", context.packageName)
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        workoutViewModel.onEvent(
                            WorkoutEvent.AddWorkoutExerciseCrossRef(
                                workoutSession = workoutSession,
                                exerciseId = exercise.exerciseId
                            )
                        )

                        onExerciseSelected(exercise.exerciseName)

                        navController.popBackStack()
                    }
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = "image of exercise",
                    modifier = Modifier
                        .size(32.dp)
                )
                Text(
                    exercise.exerciseName,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                    )
            }
        }
    }
}