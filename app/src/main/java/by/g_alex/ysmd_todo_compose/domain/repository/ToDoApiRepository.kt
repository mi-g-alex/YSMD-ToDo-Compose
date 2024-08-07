package by.g_alex.ysmd_todo_compose.domain.repository

import by.g_alex.ysmd_todo_compose.data.remote.dto.AddOrUpdateElementByIdDto
import by.g_alex.ysmd_todo_compose.data.remote.dto.GetAllToDoListDto
import by.g_alex.ysmd_todo_compose.data.remote.dto.GetToDoByIdResponseDto
import by.g_alex.ysmd_todo_compose.data.remote.dto.PatchAllToDoRequestDto

interface ToDoApiRepository {

    /**
     * Get All Todos.
     * @return [GetAllToDoListDto]
     * **/
    suspend fun getAllToDo(): GetAllToDoListDto

    /**
     * Get To Do by [id].
     * @return item [GetToDoByIdResponseDto]
     * **/
    suspend fun getToDoById(id: String): GetToDoByIdResponseDto

    /**
     * Patch list of ToDos.
     * @return new [GetAllToDoListDto]
     * **/
    suspend fun patchAllToDos(item: PatchAllToDoRequestDto): GetAllToDoListDto

    /**
     * Add NEW item to list.
     * @return new item [GetToDoByIdResponseDto]
     * **/
    suspend fun addNewToDo(item: AddOrUpdateElementByIdDto): GetToDoByIdResponseDto

    /**
     * Edit item to list.
     * @return updated item [GetToDoByIdResponseDto]
     * **/
    suspend fun updateToDo(id: String, item: AddOrUpdateElementByIdDto): GetToDoByIdResponseDto

    /**
     * Delete item from list.
     * @return deleted item [GetToDoByIdResponseDto]
     * **/
    suspend fun deleteToDo(id: String): GetToDoByIdResponseDto
}