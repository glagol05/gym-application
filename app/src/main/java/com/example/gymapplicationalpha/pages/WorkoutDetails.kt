package com.example.gymapplicationalpha.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.gymapplicationalpha.data.entity.WorkoutExerciseSet
import com.example.gymapplicationalpha.data.events.ExerciseEvent
import com.example.gymapplicationalpha.data.joins.WorkoutWithSets
import com.example.gymapplicationalpha.data.viewmodels.WorkoutExerciseSetViewModel

@Composable
fun WorkoutDetails(
    navController: NavController,
    workoutSession: Int,
) {

    val context = LocalContext.current
    val appDatabase = AppDatabase.getInstance(context)

    val workoutDao = appDatabase.workoutDao
    val exerciseDao = appDatabase.exerciseDao
    val setDao = appDatabase.workoutExerciseSetDao

    val workoutViewModel: WorkoutViewModel = remember {
        WorkoutViewModel(workoutDao = workoutDao)
    }

    val exerciseViewModel: ExerciseViewModel = remember {
        ExerciseViewModel(exerciseDao = exerciseDao)
    }

    val setViewModel: WorkoutExerciseSetViewModel = remember {
        WorkoutExerciseSetViewModel(workoutExerciseSetDao = setDao)
    }

    val workoutWithExercises by workoutViewModel.getExercisesForWorkout(workoutSession)
        .collectAsState(initial = null)

    val workout = workoutWithExercises?.workout
    val exercisesForWorkout: List<Exercise> =
        (workoutWithExercises?.exercises ?: emptyList<Exercise>()) as List<Exercise>


    Column(modifier = Modifier.padding(top = 64.dp)) {
        AddExerciseRow(onAddExerciseClicked = {
            navController.navigate(Screen.AddExerciseScreen.passSession(workoutSession))
        })
        Text(text = workout?.workoutType ?: "")
        Text(text = workout?.date ?: "")

        exercisesForWorkout.forEach { exercise ->
            val sets: List<WorkoutExerciseSet> by setViewModel
                .getSetsForExerciseInWorkout(workoutSession, exercise.exerciseId)
                .collectAsState(initial = emptyList<WorkoutExerciseSet>())

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = exercise.exerciseName,
                    modifier = Modifier.weight(1f)
                )
                Button(
                    onClick = {
                        exerciseViewModel.onEvent(
                            ExerciseEvent.RemoveExerciseFromWorkout(
                                workoutSession,
                                exercise.exerciseId
                            )
                        )
                    },
                    modifier = Modifier.size(20.dp)
                ) {
                    Text("R")
                }
            }

            var weight by remember {
                mutableStateOf("ll")
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, top = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                var initialSet by remember {
                    mutableStateOf("YYY")
                }

                Text(
                    text = "Weight: "
                )

                BasicTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                )
            }

            for (i in 1..3) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, top = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var initialSet by remember {
                        mutableStateOf("YYY")
                    }
                    BasicTextField(
                        value = initialSet,
                        onValueChange = { initialSet = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent)
                    )
                }
            }
            sets.forEach { set ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, top = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    var initialSet by remember {
                        mutableStateOf("YYY")
                    }
                    TextField(
                        value = initialSet,
                        onValueChange = { initialSet = it }
                    )
                    Text(text = "Set ${set.setNumber}: ${set.repNumber} reps @ ${set.weight ?: 0f} kg")
                }
            }
        }
        Spacer(modifier = Modifier
            .padding(10.dp)
        )
        Button(
            onClick = {
                System.out.print(1)
            },
            modifier = Modifier
                .padding(start = 18.dp)
                .size(height = 35.dp, width = 80.dp)
        ) {
            Text("Save")
        }
    }
}