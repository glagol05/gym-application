package com.example.gymapplicationalpha.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.gymapplicationalpha.data.events.WorkoutEvent
import com.example.gymapplicationalpha.data.events.WorkoutExerciseSetEvent
import com.example.gymapplicationalpha.data.joins.WorkoutWithSets
import com.example.gymapplicationalpha.data.viewmodels.WorkoutExerciseSetViewModel
import java.time.Clock.offset
import androidx.compose.foundation.lazy.items

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

    val workoutViewModel: WorkoutViewModel = remember { WorkoutViewModel(workoutDao) }
    val exerciseViewModel: ExerciseViewModel = remember { ExerciseViewModel(exerciseDao) }
    val setViewModel: WorkoutExerciseSetViewModel = remember {
        WorkoutExerciseSetViewModel(setDao)
    }

    val workoutWithExercises by workoutViewModel
        .getExercisesForWorkout(workoutSession)
        .collectAsState(initial = null)

    val workout = workoutWithExercises?.workout

    val exercisesForWorkout: List<Exercise> =
        (workoutWithExercises?.exercises ?: emptyList()) as List<Exercise>

    val orderedExercisesForWorkout by workoutViewModel
        .getOrderedExercisesForWorkout(workoutSession)
        .collectAsState(initial = emptyList<Exercise>())

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 64.dp)
            .systemBarsPadding()
    ) {

        item {
            AddExerciseRow(onAddExerciseClicked = {
                navController.navigate(Screen.AddExerciseScreen.passSession(workoutSession))
            })
        }

        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = workout?.workoutType ?: "",
                    style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
                )

                Spacer(modifier = Modifier.padding(32.dp))

                Text(
                    text = workout?.date ?: "",
                    style = TextStyle(fontSize = 22.sp, fontWeight = FontWeight.Bold)
                )
            }
        }

        item {
            Button(
                onClick = {
                    if (workout == null) return@Button
                    workoutViewModel.onEvent(WorkoutEvent.DeleteWorkout(workout))
                    workoutViewModel.onEvent(WorkoutEvent.DeleteCrossRefByWorkout(workoutSession))
                    setViewModel.onEvent(
                        WorkoutExerciseSetEvent.deleteAlLSetsByWorkoutId(workoutSession)
                    )
                    navController.popBackStack()
                }
            ) { }
        }

        items(orderedExercisesForWorkout) { exercise ->

            val imageResId = remember(exercise.imageName) {
                context.resources.getIdentifier(
                    exercise.imageName,
                    "drawable",
                    context.packageName
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = imageResId),
                    contentDescription = "exercise image",
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = exercise.exerciseName,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
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

            val sets: List<WorkoutExerciseSet> by setViewModel
                .getSetsForExerciseInWorkout(workoutSession, exercise.exerciseId)
                .collectAsState(initial = emptyList<WorkoutExerciseSet>())

            val weightState = remember(sets) {
                mutableStateOf(sets.firstOrNull()?.weight?.toString() ?: "")
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 24.dp, top = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Weight: ")

                BasicTextField(
                    value = weightState.value,
                    onValueChange = { weightState.value = it },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            val setStates = remember(sets) {
                List(3) { i ->
                    mutableStateOf(
                        sets.getOrNull(i)?.repNumber?.toString() ?: ""
                    )
                }
            }

            for (i in 0 until 3) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, top = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Set ${i + 1}: ")

                    BasicTextField(
                        value = setStates[i].value,
                        onValueChange = { setStates[i].value = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(modifier = Modifier.padding(10.dp))

            Button(
                onClick = {
                    setStates.forEachIndexed { index, state ->
                        val reps = state.value.toIntOrNull() ?: 0
                        setViewModel.onEvent(
                            WorkoutExerciseSetEvent.saveSet(
                                workoutSession = workoutSession,
                                exerciseId = exercise.exerciseId,
                                setNumber = index + 1,
                                repNumber = reps,
                                weight = weightState.value
                            )
                        )
                    }
                },
                modifier = Modifier
                    .padding(start = 18.dp)
                    .size(80.dp, 35.dp)
            ) {
                Text("Save")
            }
        }
    }
}