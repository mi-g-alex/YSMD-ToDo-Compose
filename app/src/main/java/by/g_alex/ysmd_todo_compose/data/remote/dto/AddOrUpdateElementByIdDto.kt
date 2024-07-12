package by.g_alex.ysmd_todo_compose.data.remote.dto

import com.google.gson.annotations.SerializedName

data class AddOrUpdateElementByIdDto(
    @SerializedName("element")
    val element: ToDoItemDto,
)