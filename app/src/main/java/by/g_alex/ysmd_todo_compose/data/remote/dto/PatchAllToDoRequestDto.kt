package by.g_alex.ysmd_todo_compose.data.remote.dto

import com.google.gson.annotations.SerializedName

data class PatchAllToDoRequestDto(
    @SerializedName("list")
    val list: List<ToDoItemDto>,
)
