package com.example.gymapplicationalpha.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymapplicationalpha.R
import com.example.gymapplicationalpha.Screen
import com.example.gymapplicationalpha.components.LargeButton
import com.example.gymapplicationalpha.ui.theme.GymApplicationAlphaTheme

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainScreen(navController: NavController) {
    GymApplicationAlphaTheme {
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
                        imageRes = R.drawable.exercise,
                        text = "Add Workout",
                        onClick = { navController.navigate(Screen.AddWorkout.route) }
                    )
                    LargeButton(
                       imageRes = R.drawable.statistics,
                        text = "Statistics",
                        onClick = { navController.navigate(Screen.Statistics.route) }
                    )
                    LargeButton(
                        imageRes = R.drawable.calendar,
                        text = "Calendar",
                        onClick = { navController.navigate(Screen.Calendar.route) }
                    )
                    LargeButton(
                        imageRes = R.drawable.settings,
                        text = "Settings",
                        onClick = { navController.navigate(Screen.Settings.route) }
                    )
                    LargeButton(
                        imageRes = R.drawable.exercise,
                        text = "Add Exercise",
                        onClick = { navController.navigate(Screen.AddExercise.route) }
                    )
                }
            }
        }
    }
}