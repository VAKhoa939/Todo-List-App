package com.example.todolist.data

import java.util.Calendar
import java.util.Date

data class AddTodoPopupUIState (
    var name: String = "",
    var selectedDate: Date = Calendar.getInstance().time,
    var isDefaultDate: Boolean = true
)