package by.g_alex.ysmd_todo_compose.data.remote.dto

import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority
import by.g_alex.ysmd_todo_compose.domain.model.ToDoItemModel
import com.google.gson.annotations.SerializedName
import java.util.Date

data class ToDoItemDto(
    @SerializedName("id")
    val id: String,

    @SerializedName("text")
    val text: String,

    /** importance = low | basic | important **/
    @SerializedName("importance")
    val priority: String,

    @SerializedName("deadline")
    val deadline: Long?,

    @SerializedName("done")
    val completed: Boolean,

    @SerializedName("created_at")
    val createAt: Long,

    @SerializedName("changed_at")
    val changedAt: Long,

    @SerializedName("files")
    val files: List<String>?,

    @SerializedName("last_updated_by")
    val lastUpdateBy: Any?
) {
    fun toModel() = ToDoItemModel(
        id = id,
        text = text,
        priority = ToDoPriority.fromString(priority),
        deadline = deadline?.let { Date(it) },
        completed = completed,
        createDate = Date(createAt),
        updateDate = Date(changedAt)
    )
}
