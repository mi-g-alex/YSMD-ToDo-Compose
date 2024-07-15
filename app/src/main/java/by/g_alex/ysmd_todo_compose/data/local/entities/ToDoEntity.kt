package by.g_alex.ysmd_todo_compose.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority
import by.g_alex.ysmd_todo_compose.domain.model.ToDoItemModel
import java.util.Date

@Entity
data class ToDoEntity(
    @PrimaryKey
    val id: String,
    val text: String,
    val priority: String,
    val deadline: Long?,
    val completed: Boolean,
    val createDate: Long,
    val updateDate: Long?
) {
    fun toModel() = ToDoItemModel(
        id = id,
        text = text,
        priority = ToDoPriority.fromString(priority),
        deadline = deadline?.let { Date(it) },
        completed = completed,
        createDate = Date(createDate),
        updateDate = updateDate?.let { Date(it) }
    )
}
