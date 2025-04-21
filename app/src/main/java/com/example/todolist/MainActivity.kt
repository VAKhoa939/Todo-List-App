package com.example.todolist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.todolist.ui.theme.TodoListTheme
import com.example.todolist.ui.mainPage.MainPage

/**
 * Name: Võ Anh Khoa
 * ID: 21110046
 * Class: 02FIE - Tuesday Afternoon
 * Date: 14/4/2025
 * Assignment 7: To-do List app
 */

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoListTheme {
                TodoListApp()
            }
        }
    }
}

@Composable
fun TodoListApp() {
    MainPage(
        modifier = Modifier
            .fillMaxSize()
    )
}