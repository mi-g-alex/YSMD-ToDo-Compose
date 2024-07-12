package by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo

import androidx.annotation.StringRes
import by.g_alex.ysmd_todo_compose.domain.model.ToDoItemModel

data class CreateEditToDoState(
    val item: ToDoItemModel? = null,
    val isLoading: Boolean = false,
    @StringRes val isError: Int? = null
)