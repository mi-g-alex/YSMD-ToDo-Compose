package by.g_alex.ysmd_todo_compose.domain.repository

import by.g_alex.ysmd_todo_compose.data.remote.dto.AddOrUpdateElementByIdDto
import by.g_alex.ysmd_todo_compose.data.remote.dto.GetAllToDoListDto
import by.g_alex.ysmd_todo_compose.data.remote.dto.GetToDoByIdResponseDto
import by.g_alex.ysmd_todo_compose.data.remote.dto.PatchAllToDoRequestDto

interface ToDoApiRepository {

    /** Get All Todos. Return list [GetAllToDoListDto] **/
    suspend fun getAllToDo(): GetAllToDoListDto

    /** Get To Do by [id]. Return item [GetToDoByIdResponseDto] **/
    suspend fun getToDoById(id: String): GetToDoByIdResponseDto

    /** Patch list of ToDos. Return new list [GetAllToDoListDto] **/
    suspend fun patchAllToDos(item: PatchAllToDoRequestDto): GetAllToDoListDto

    /** Add NEW item to list. Return new item [GetToDoByIdResponseDto]  **/
    suspend fun addNewToDo(item: AddOrUpdateElementByIdDto): GetToDoByIdResponseDto

    /** Edit item to list. Return updated item [GetToDoByIdResponseDto]  **/
    suspend fun updateToDo(id: String, item: AddOrUpdateElementByIdDto): GetToDoByIdResponseDto

    /** Delete item from list. Return deleted item [GetToDoByIdResponseDto]  **/
    suspend fun deleteToDo(id: String): GetToDoByIdResponseDto
}