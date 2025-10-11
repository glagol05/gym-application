package com.example.gymapplication.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymapplication.R
import com.example.gymapplication.Screen
import com.example.gymapplication.components.DatePicker
import com.example.gymapplication.components.LargeButton
import android.net.Uri
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import java.util.Calendar

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddWorkout(navController : NavController) {
    val calendar = Calendar.getInstance()
    val today = "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${calendar.get(Calendar.YEAR)}"

    var selectedWorkout by remember { mutableStateOf("") }
    var selectedDate by remember { mutableStateOf(today) }

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            FlowRow(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(innerPadding),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                LargeButton(
                    imageRes = R.drawable.odlaw,
                    text = "Push",
                    onClick = {
                        selectedWorkout = "Push"
                        navController.navigate(Screen.AddWorkoutAdvanced.withArgs(selectedWorkout, Uri.encode(selectedDate)))
                    }
                )
                LargeButton(
                    imageRes = R.drawable.wenda,
                    text = "Pull",
                    onClick = {
                        selectedWorkout = "Pull"
                        navController.navigate(Screen.AddWorkoutAdvanced.withArgs(selectedWorkout, Uri.encode(selectedDate)))
                    }
                )
                LargeButton(
                    imageRes = R.drawable.odlaw,
                    text = "Legs",
                    onClick = {
                        selectedWorkout = "Legs"
                        navController.navigate(Screen.AddWorkoutAdvanced.withArgs(selectedWorkout, Uri.encode(selectedDate)))
                    }
                )
                DatePicker(
                    selectedDate = selectedDate,
                    onDateSelected = { newDate -> selectedDate = newDate }
                )
            }
        }
    }
}