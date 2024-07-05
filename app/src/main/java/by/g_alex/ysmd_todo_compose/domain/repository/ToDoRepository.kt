package by.g_alex.ysmd_todo_compose.domain.repository

import by.g_alex.ysmd_todo_compose.common.Resource
import by.g_alex.ysmd_todo_compose.domain.model.ToDoItemModel
import kotlinx.coroutines.flow.Flow

interface ToDoRepository {

    val listOfToDo: List<ToDoItemModel>

    fun getAllToDo(): Flow<Resource<List<ToDoItemModel>>>

    fun getToDoById(id: String?): Flow<Resource<ToDoItemModel?>>

    fun addOrEditToDo(item: ToDoItemModel): Flow<Resource<Nothing>>

    fun deleteToDo(item: ToDoItemModel): Flow<Resource<Nothing>>

    suspend fun countOfCompleted(): Int

}
