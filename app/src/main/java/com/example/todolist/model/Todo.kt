package com.example.todolist.model

import java.util.Date

data class Todo(
    val id: Int,
    val name: String,
    val date: Date,
    val isCompleted: Boolean = false
)
