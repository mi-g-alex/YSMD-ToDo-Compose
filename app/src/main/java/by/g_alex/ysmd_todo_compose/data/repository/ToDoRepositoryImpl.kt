package by.g_alex.ysmd_todo_compose.data.repository

import by.g_alex.ysmd_todo_compose.common.Resource
import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority
import by.g_alex.ysmd_todo_compose.data.listHardCoded
import by.g_alex.ysmd_todo_compose.data.model.ToDoItemModel
import by.g_alex.ysmd_todo_compose.domain.ToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor() : ToDoRepository {

    private val _listOfToDo = mutableListOf<ToDoItemModel>()
    override val listOfToDo: List<ToDoItemModel>
        get() = _listOfToDo.toList()

    init {
        _listOfToDo.addAll(listHardCoded)
    }

    override suspend fun getToDoById(id: String?) = _listOfToDo.firstOrNull { it.id == id }

    override fun getAllToDo(): Flow<Resource<List<ToDoItemModel>>> = flow {
        try {
            emit(Resource.Loading())
            val data = listOfToDo
            emit(Resource.Success(data))
        } catch (ex: Exception) {
            emit(Resource.Error(ex.message.toString()))
        }
    }

    override suspend fun addOrEditToDo(item: ToDoItemModel) {
        if (item.id != null) {
            _listOfToDo.replaceAll { if (it.id == item.id) item else it }
        } else {
            _listOfToDo.add(0, item.copy(id = Calendar.getInstance().time.toString()))

        }
    }

    override suspend fun deleteToDo(item: ToDoItemModel) {
        _listOfToDo.remove(item)
    }

    override suspend fun countOfCompleted() = listOfToDo.count { it.completed }


}
