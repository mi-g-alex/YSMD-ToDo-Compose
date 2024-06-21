package by.g_alex.ysmd_todo_compose.data.model

import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority
import java.util.Calendar
import java.util.Date

data class ToDoItemModel(
    val id: String? = null,
    val text: String,
    val priority: ToDoPriority,
    val deadline: Date? = null,
    val completed: Boolean = false,
    val createDate: Date = Calendar.getInstance().time,
    val updateDate: Date? = null
)