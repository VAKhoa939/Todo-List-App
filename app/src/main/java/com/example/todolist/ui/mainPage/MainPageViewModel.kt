package com.example.todolist.ui.mainPage

import androidx.lifecycle.ViewModel
import com.example.todolist.data.TodoListSample
import com.example.todolist.data.MainPageUIState
import com.example.todolist.model.Todo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date

class MainPageViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MainPageUIState())
    val uiState: StateFlow<MainPageUIState> = _uiState.asStateFlow()

    private var nextId: Int

    private fun getSampleData() {
        _uiState.update { current ->
            current.copy(todoList = TodoListSample().getTodoSample())
        }
    }

    init {
        getSampleData()
        nextId = _uiState.value.todoList.size
    }
    
    fun addTodo(name: String, date: Date) {
        val newTodo = Todo(
            id = nextId++,
            name = name,
            date = date
        )
        _uiState.update { current ->
            current.copy(
                todoList = current.todoList + newTodo
            )
        }
    }

    fun updateTodo(updatedTodo: Todo) {
        _uiState.update { current ->
            current.copy(
                todoList = current.todoList.map {
                    if (it.id == updatedTodo.id) updatedTodo else it
                }
            )
        }
    }

    fun deleteTodo(id: Int) {
        _uiState.update { current ->
            current.copy(
                todoList = current.todoList.filter { it.id != id }
            )
        }
    }

    fun sortByName() {
        _uiState.update { current ->
            current.copy(
                todoList = current.todoList.sortedBy { it.name }
            )
        }
    }

    fun sortByDate() {
        _uiState.update { current ->
            current.copy(
                todoList = current.todoList.sortedBy { it.date }
            )
        }
    }
    
    fun setMenuExpanded(value: Boolean) {
        _uiState.update { current ->
            current.copy(
                menuExpanded = value
            )
        }
    }

    fun setShowAddTodoPopup(value: Boolean) {
        _uiState.update { current ->
            current.copy(
                showAddTodoPopup = value
            )
        }
    }
}