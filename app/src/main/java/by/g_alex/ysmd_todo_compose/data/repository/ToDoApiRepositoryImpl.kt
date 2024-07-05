package by.g_alex.ysmd_todo_compose.data.repository

import by.g_alex.ysmd_todo_compose.data.remote.ToDoAPI
import by.g_alex.ysmd_todo_compose.data.remote.dto.AddOrUpdateElementByIdDto
import by.g_alex.ysmd_todo_compose.data.remote.dto.GetAllToDoListDto
import by.g_alex.ysmd_todo_compose.data.remote.dto.GetToDoByIdResponseDto
import by.g_alex.ysmd_todo_compose.data.remote.dto.PatchAllToDoRequestDto
import by.g_alex.ysmd_todo_compose.domain.repository.ToDoApiRepository
import javax.inject.Inject

class ToDoApiRepositoryImpl @Inject constructor(
    private val api: ToDoAPI
) : ToDoApiRepository {
    override suspend fun getAllToDo(): GetAllToDoListDto =
        api.getAllToDo()

    override suspend fun getToDoById(id: String): GetToDoByIdResponseDto =
        api.getToDoById(id)

    override suspend fun patchAllToDos(item: PatchAllToDoRequestDto): GetAllToDoListDto =
        api.patchAllToDos(item)

    override suspend fun addNewToDo(item: AddOrUpdateElementByIdDto): GetToDoByIdResponseDto =
        api.addNewToDo(item)

    override suspend fun updateToDo(
        id: String,
        item: AddOrUpdateElementByIdDto
    ): GetToDoByIdResponseDto =
        api.updateToDo(id, item)

    override suspend fun deleteToDo(id: String): GetToDoByIdResponseDto =
        api.deleteToDo(id)
}