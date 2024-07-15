package by.g_alex.ysmd_todo_compose.data.repository

import by.g_alex.ysmd_todo_compose.data.local.ToDoDao
import by.g_alex.ysmd_todo_compose.data.remote.dto.AddOrUpdateElementByIdDto
import by.g_alex.ysmd_todo_compose.data.remote.dto.GetAllToDoListDto
import by.g_alex.ysmd_todo_compose.data.remote.dto.GetToDoByIdResponseDto
import by.g_alex.ysmd_todo_compose.data.remote.dto.PatchAllToDoRequestDto
import by.g_alex.ysmd_todo_compose.domain.model.ToDoItemModel
import by.g_alex.ysmd_todo_compose.domain.repository.ToDoApiRepository
import by.g_alex.ysmd_todo_compose.domain.repository.ToDoDataBaseRepository
import javax.inject.Inject

class ToDoDataBaseRepositoryImpl @Inject constructor(
    private val dao: ToDoDao
) : ToDoDataBaseRepository {

    override suspend fun getAllToDo(): List<ToDoItemModel> =
        dao.getAllToDo().map { it.toModel() }

    override suspend fun updateAllToDos(todos: List<ToDoItemModel>) {
        dao.deleteAllToDo()
        dao.addAllToDos(todos.map { it.toEntity() })
    }

    override suspend fun addToDo(item: ToDoItemModel) {
        dao.addToDo(item.toEntity())
    }

    override suspend fun deleteToDo(id: String) {
        dao.deleteToDo(id)
    }

    override suspend fun updateToDo(id: String, item: ToDoItemModel) {
        dao.editToDo(item.toEntity())
    }
}