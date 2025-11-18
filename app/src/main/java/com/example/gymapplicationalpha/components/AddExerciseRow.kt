package com.example.gymapplicationalpha.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun AddExerciseRow(onAddExerciseClicked: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 32.dp, start = 16.dp, end = 16.dp)
            .clickable { onAddExerciseClicked() }
            .background(Color.Gray),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "Add Exercise",
            modifier = Modifier.padding(end = 8.dp)
        )
        Text("Add Exercise")
    }
}

//@Composable
//fun AddExerciseRow(onAddExerciseClicked: () -> Unit) {
//    Button(
//        onClick = { onAddExerciseClicked() },
//        shape = RoundedCornerShape(4.dp),
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 32.dp, start = 16.dp, end = 16.dp),
//    ) {
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            Icon(
//                imageVector = Icons.Default.Add,
//                contentDescription = "Add Exercise",
//                modifier = Modifier.padding(end = 8.dp)
//            )
//            Text("Add Exercise")
//        }
//    }
//}