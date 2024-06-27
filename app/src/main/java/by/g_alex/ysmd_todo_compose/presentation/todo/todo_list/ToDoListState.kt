package by.g_alex.ysmd_todo_compose.presentation.todo.todo_list

import by.g_alex.ysmd_todo_compose.data.model.ToDoItemModel

data class ToDoListState(
    val listOfToDo: List<ToDoItemModel> = emptyList(),
    val cnt: Int = 0,
    val isLoading: Boolean = false,
    val isError: String? = null
)