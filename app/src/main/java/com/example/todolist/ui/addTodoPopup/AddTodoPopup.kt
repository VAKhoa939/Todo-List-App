@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.todolist.ui.addTodoPopup

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.TimePicker
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.example.todolist.data.AddTodoPopupUIState
import com.example.todolist.utils.getFormattedDate
import java.util.Calendar
import java.util.Date
import java.util.Locale

@Composable
fun AddTodoPopup(
    onDismiss: () -> Unit,
    onAddTodo: (String, Date) -> Unit
) {
    val viewModel: AddTodoPopupViewModel = AddTodoPopupViewModel()
    val uiState: State<AddTodoPopupUIState> = viewModel.uiState.collectAsState()
    val context = LocalContext.current

    BasicAlertDialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = false)
    ) {
        Surface(
            tonalElevation = 8.dp,
            shape = RoundedCornerShape(12.dp),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Add New Todo")
                Spacer(Modifier.height(8.dp))

                OutlinedTextField(
                    value = uiState.value.name,
                    onValueChange = {
                        viewModel.setName(it)
                    },
                    label = { Text("Todo Name") }
                )
                Spacer(Modifier.height(8.dp))

                ChooseDateButton(
                    context = context,
                    selectedDate = uiState.value.selectedDate,
                    isDefaultDate = uiState.value.isDefaultDate,
                    onDateSelected = { date ->
                        viewModel.setDate(date)
                    }
                )
                Spacer(Modifier.height(16.dp))
                BottomButtons(
                    onDismiss = onDismiss,
                    onAdd = {
                        onAddTodo(
                            uiState.value.name,
                            uiState.value.selectedDate
                        )
                        onDismiss()
                    }
                )
            }
        }
    }
}

@Composable
fun ChooseDateButton(
    context: Context,
    selectedDate: Date,
    isDefaultDate: Boolean,
    onDateSelected: (date: Date) -> Unit,
    modifier: Modifier = Modifier
) {
    val locale = Locale.getDefault()
    val calendar = Calendar.getInstance().apply {
        time = selectedDate
    }
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)
    val hour = calendar.get(Calendar.HOUR_OF_DAY)
    val minute = calendar.get(Calendar.MINUTE)

    Column {
        Button(onClick = {
            showDatePicker(
                context,
                year,
                month,
                day
            ) { year, month, day ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day)

                showTimePicker(
                    context,
                    hour,
                    minute
                ) { hour, minute ->
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    onDateSelected(calendar.time)
                }
            }
        }) {
            Text(text = "Pick Date & Time")
        }
        if (!isDefaultDate) {
            Spacer(Modifier.height(8.dp))
            Text(text = getFormattedDate(locale, selectedDate))
        }
    }
}

fun showDatePicker(context: Context, year: Int, month: Int, day: Int, onDateSelected: (year: Int, month: Int, day: Int) -> Unit) {
    val datePickerDialog = DatePickerDialog(
        context,
        { _, selectedYear, selectedMonth, selectedDayOfMonth ->
            onDateSelected(selectedYear, selectedMonth, selectedDayOfMonth)
        },
        year,
        month,
        day
    )
    datePickerDialog.show()
}

fun showTimePicker(context: Context, hour: Int, minute: Int, onTimeSelected: (Int, Int) -> Unit) {
    val timePickerDialog = TimePickerDialog(
        context,
        { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
            onTimeSelected(selectedHour, selectedMinute)
        },
        hour,
        minute,
        true
    )
    timePickerDialog.show()
}

@Composable
fun BottomButtons(
    onDismiss: () -> Unit,
    onAdd: () -> Unit
) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier.fillMaxWidth()
    ) {
        TextButton(onClick = onDismiss) {
            Text("Cancel")
        }
        Spacer(modifier = Modifier.width(8.dp))

        TextButton(onClick = {
            onAdd()
        }) {
            Text("Add")
        }
    }
}