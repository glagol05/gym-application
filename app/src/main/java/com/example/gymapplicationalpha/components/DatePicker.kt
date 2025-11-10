import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun SimpleDatePickerField(
    label: String = "Select date",
    modifier: Modifier = Modifier,
    onDateSelected: (Long) -> Unit
) {
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }
    var selectedDateMillis by remember { mutableStateOf<Long?>(null) }

    val displayText = selectedDateMillis?.let {
        SimpleDateFormat("MM/dd/yyyy", Locale.getDefault()).format(Date(it))
    } ?: ""

    OutlinedTextField(
        value = displayText,
        onValueChange = { },
        readOnly = true,
        label = { Text(label) },
        modifier = modifier.clickable { showDialog = true }
    )

    if (showDialog) {
        LaunchedEffect(Unit) {
            val calendar = Calendar.getInstance()
            android.app.DatePickerDialog(
                context,
                { _, year, month, dayOfMonth ->
                    calendar.set(year, month, dayOfMonth)
                    selectedDateMillis = calendar.timeInMillis
                    onDateSelected(calendar.timeInMillis)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).apply {
                setOnCancelListener { showDialog = false }
                show()
            }
            showDialog = false
        }
    }
}

