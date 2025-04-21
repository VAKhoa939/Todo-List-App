package com.example.todolist.ui.addTodoPopup

import androidx.lifecycle.ViewModel
import com.example.todolist.data.AddTodoPopupUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date

class AddTodoPopupViewModel: ViewModel() {
    private val _uiState= MutableStateFlow(AddTodoPopupUIState())
    val uiState: StateFlow<AddTodoPopupUIState> = _uiState.asStateFlow()

    fun setName(value: String) {
        _uiState.update { current ->
            current.copy(
                name = value
            )
        }
    }

    fun setDate(value: Date) {
        _uiState.update { current ->
            current.copy(
                selectedDate = value,
                isDefaultDate = false
            )
        }
    }
}