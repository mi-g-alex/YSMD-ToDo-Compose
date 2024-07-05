package by.g_alex.ysmd_todo_compose.data.remote.dto

import com.google.gson.annotations.SerializedName

data class GetToDoByIdResponseDto(
    @SerializedName("status")
    val status: String,

    @SerializedName("element")
    val element: ToDoItemDto,

    @SerializedName("revision")
    val revision: Int,
)
