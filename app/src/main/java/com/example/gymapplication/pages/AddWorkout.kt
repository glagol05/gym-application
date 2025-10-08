package com.example.gymapplication.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymapplication.R
import com.example.gymapplication.Screen
import com.example.gymapplication.components.LargeButton

@Composable
fun AddWorkout(navController : NavController) {
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
                    onClick = {navController.navigate(Screen.AddWorkoutAdvanced.route)}
                )
                LargeButton(
                    imageRes = R.drawable.wenda,
                    text = "Pull",
                    onClick = {navController.navigate(Screen.AddWorkoutAdvanced.route)}
                )
                LargeButton(
                    imageRes = R.drawable.odlaw,
                    text = "Legs",
                    onClick = {navController.navigate(Screen.AddWorkoutAdvanced.route)}
                )
            }
        }
    }
}