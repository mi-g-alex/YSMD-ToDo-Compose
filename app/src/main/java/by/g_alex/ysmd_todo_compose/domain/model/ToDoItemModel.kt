package by.g_alex.ysmd_todo_compose.domain.model

import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority
import by.g_alex.ysmd_todo_compose.data.additional.enums.toDtoString
import by.g_alex.ysmd_todo_compose.data.local.entities.ToDoEntity
import by.g_alex.ysmd_todo_compose.data.remote.dto.ToDoItemDto
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
) {
    fun toDto(): ToDoItemDto {
        val time = Calendar.getInstance().time.time
        return ToDoItemDto(
            id = id ?: time.toString(),
            text = text,
            priority = priority.toDtoString(),
            deadline = deadline?.time,
            completed = completed,
            createAt = createDate.time,
            changedAt = updateDate?.time ?: createDate.time,
            files = null,
            // ToDo: FIX IT
            lastUpdateBy = "android_id"
        )
    }

    fun toEntity() : ToDoEntity {
        val time = Calendar.getInstance().time.time
        return ToDoEntity(
            id = id ?: time.toString(),
            text = text,
            priority = priority.toDtoString(),
            deadline = deadline?.time,
            completed = completed,
            createDate = createDate.time,
            updateDate = updateDate?.time,
        )
    }

}