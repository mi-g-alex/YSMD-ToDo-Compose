package by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo

import by.g_alex.ysmd_todo_compose.data.model.ToDoItemModel

data class CreateEditToDoState(
    val item: ToDoItemModel? = null,
    val isLoading: Boolean = false,
    val isError: String? = null
)