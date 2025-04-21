@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.todolist.ui.mainPage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.data.MainPageUIState
import com.example.todolist.model.Todo
import com.example.todolist.ui.addTodoPopup.AddTodoPopup
import com.example.todolist.utils.getFormattedDate
import java.util.Locale

@Preview(showBackground = true)
@Composable
fun MainPage(modifier: Modifier = Modifier) {
    val viewModel: MainPageViewModel = viewModel()
    val uiState: State<MainPageUIState> = viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TodoListTopAppBar(
                onSortByName = {
                    viewModel.sortByName()
                },
                onSortByDate = {
                    viewModel.sortByDate()
                },
                expanded = uiState.value.menuExpanded,
                onSetExpanded = {
                    viewModel.setMenuExpanded(it)
                }
            )
        },
        floatingActionButton = {
            AddTodoFloatingActionButton(
                onClick = {
                    viewModel.setShowAddTodoPopup(true)
                }
            )
        },
    ) { innerPadding ->
        Column {
            TodoListLazyColumn(
                todoList = uiState.value.todoList,
                onTodoCheckedChange = {
                    viewModel.updateTodo(it)
                },
                onTodoDelete = {
                    viewModel.deleteTodo(it)
                },
                modifier = modifier
                    .padding(innerPadding)
            )
        }

        if (uiState.value.showAddTodoPopup) {
            AddTodoPopup(
                onDismiss = {
                    viewModel.setShowAddTodoPopup(false)
                },
                onAddTodo = { name, date ->
                    viewModel.addTodo(name, date)
                }
            )
        }
    }
}

@Composable
fun TodoListTopAppBar(
    onSortByName: () -> Unit,
    onSortByDate: () -> Unit,
    expanded: Boolean,
    onSetExpanded: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(
                text = "To-Do List"
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        ),
        actions = {
            TodoSortMenu(
                onSortByName,
                onSortByDate,
                expanded,
                onSetExpanded
            )
        }
    )
}

@Composable
fun TodoSortMenu(
    onSortByName: () -> Unit,
    onSortByDate: () -> Unit,
    expanded: Boolean,
    onSetExpanded: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Box {
        IconButton(
            onClick = {
                onSetExpanded(true)
            }
        ) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Menu",
                tint = Color.White
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                onSetExpanded(false)
            }
        ) {
            DropdownMenuItem(
                text = {
                    Text("Sort by Name")
                },
                onClick = {
                    onSortByName()
                }
            )

            DropdownMenuItem(
                text = {
                    Text("Sort by Date")
                },
                onClick = {
                    onSortByDate()
                }
            )
        }
    }
}

@Composable
fun AddTodoFloatingActionButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.Add,
            contentDescription = "AddTodo"
        )
    }
}

@Composable
fun TodoListLazyColumn(
    todoList: List<Todo>,
    onTodoCheckedChange: (Todo) -> Unit,
    onTodoDelete: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        items(todoList) { todo ->
            TodoRow(
                todo = todo,
                onCheckedChange = { isChecked ->
                    val newTodo: Todo = todo.copy(isCompleted = isChecked)
                    onTodoCheckedChange(newTodo)
                },
                onDelete = {
                    onTodoDelete(todo.id)
                }
            )
        }
    }
}

@Composable
fun TodoRow(
    todo: Todo,
    onCheckedChange: (Boolean) -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier
) {
    val locale = Locale.getDefault()

    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                Color(0xFFF5F5F5),
                RoundedCornerShape(8.dp)
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = todo.isCompleted,
            onCheckedChange = onCheckedChange
        )

        Spacer(
            modifier = Modifier.width(8.dp)
        )

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = todo.name,
                fontSize = 18.sp,
                textDecoration = if (todo.isCompleted)
                    TextDecoration.LineThrough else TextDecoration.None
            )
            Text(
                text = getFormattedDate(locale, todo.date),
                fontSize = 14.sp,
                color = Color.Gray
            )
        }

        IconButton(onClick = onDelete) {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Delete",
                tint = Color.Red
            )
        }
    }
}