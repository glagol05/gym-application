package com.example.gymapplicationalpha.pages

import SimpleDatePickerField
import android.R.attr.text
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymapplicationalpha.Screen
import com.example.gymapplicationalpha.components.WorkoutCard
import com.example.gymapplicationalpha.data.AppDatabase
import com.example.gymapplicationalpha.data.events.WorkoutEvent
import com.example.gymapplicationalpha.data.viewmodels.WorkoutViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Workout(navController: NavController) {

    val mockWorkouts = listOf(
        Triple("Chest", "Bench press", 2),
        Triple("Legs", "Squat", 3),
        Triple("Back", "Pullups", 4),
        Triple("Chest", "Shoulder press", 5)
    )

    val context = LocalContext.current
    val appDatabase = AppDatabase.getInstance(context)

    val workoutDao = appDatabase.workoutDao

    val workoutViewModel: WorkoutViewModel = remember {
        WorkoutViewModel(workoutDao = workoutDao)
    }

    val state by workoutViewModel.state.collectAsState()
    val workouts = state.workouts

    var text by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(LocalDate.now()) }

    Column(
        modifier = Modifier
            .padding(top = 64.dp, start = 16.dp, end = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Workout Type") },
            keyboardOptions = KeyboardOptions(capitalization = KeyboardCapitalization.Sentences)
            )
        SimpleDatePickerField { date ->
            selectedDate = date
        }

        Button(
            onClick = {
                workoutViewModel.onEvent(
                    WorkoutEvent.SaveWorkout(
                        date = selectedDate.toString(),
                        type = text,
                        description = null
                    )
                )
            },
            shape = RoundedCornerShape(0.dp),
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text("Add workout session")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (state.workouts.isNotEmpty()) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 120.dp),
                modifier = Modifier.fillMaxWidth().systemBarsPadding(),
                contentPadding = PaddingValues(4.dp),
            ) {
                items(workouts) { workout ->
                    WorkoutCard(
                        workout.date,
                        workout.workoutType,
                        onClick = {
                            println("Clicked $workout")
                            navController.navigate(Screen.WorkoutDetails.passSession(workout.workoutSession))
                        }
                    )
                }
            }
        }
    }
}