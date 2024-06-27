package by.g_alex.ysmd_todo_compose.domain

import by.g_alex.ysmd_todo_compose.common.Resource
import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority
import by.g_alex.ysmd_todo_compose.data.model.ToDoItemModel
import by.g_alex.ysmd_todo_compose.data.repository.ToDoRepositoryImpl
import kotlinx.coroutines.flow.Flow
import java.util.Calendar
import java.util.Date

interface ToDoRepository {

    val listOfToDo: List<ToDoItemModel>

    fun getAllToDo(): Flow<Resource<List<ToDoItemModel>>>

    suspend fun getToDoById(id: String?): ToDoItemModel?

    suspend fun addOrEditToDo(item: ToDoItemModel)

    suspend fun deleteToDo(item: ToDoItemModel)

    suspend fun countOfCompleted(): Int

}
