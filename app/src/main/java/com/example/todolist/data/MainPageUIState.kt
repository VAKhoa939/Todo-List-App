package com.example.todolist.data

import androidx.compose.runtime.mutableStateListOf
import com.example.todolist.model.Todo

data class MainPageUIState(
    val todoList: List<Todo> = mutableStateListOf<Todo>(),
    val menuExpanded: Boolean = false,
    val showAddTodoPopup: Boolean = false
)
