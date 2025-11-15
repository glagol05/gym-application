package com.example.gymapplicationalpha.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.gymapplicationalpha.data.entity.Workout

@Composable
fun WorkoutDetails(
    navController: NavController,
    workoutSession: Int
) {

    Column(
        modifier = Modifier
            .padding(top = 64.dp)
    ) {
        Text(text = workoutSession.toString())
    }

}