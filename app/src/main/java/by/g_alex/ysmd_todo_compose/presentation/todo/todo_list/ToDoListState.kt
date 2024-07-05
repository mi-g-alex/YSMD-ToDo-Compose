package by.g_alex.ysmd_todo_compose.presentation.todo.todo_list

import androidx.annotation.StringRes
import by.g_alex.ysmd_todo_compose.domain.model.ToDoItemModel

data class ToDoListState(
    val listOfToDo: List<ToDoItemModel> = emptyList(),
    val isNetworkError: Boolean? = null,
    val cnt: Int = 0,
    val isLoading: Boolean = false,
    val isBearerToken: Boolean? = null,
    val authToken: String = "",
    @StringRes val isError: Int? = null
)