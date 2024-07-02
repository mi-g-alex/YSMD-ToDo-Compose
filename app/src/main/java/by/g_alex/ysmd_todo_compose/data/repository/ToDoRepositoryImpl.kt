package by.g_alex.ysmd_todo_compose.data.repository

import by.g_alex.ysmd_todo_compose.common.Resource
import by.g_alex.ysmd_todo_compose.data.listHardCoded
import by.g_alex.ysmd_todo_compose.data.model.ToDoItemModel
import by.g_alex.ysmd_todo_compose.domain.ToDoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar
import javax.inject.Inject

class ToDoRepositoryImpl @Inject constructor() : ToDoRepository {

    private val _listOfToDo = mutableListOf<ToDoItemModel>()
    override val listOfToDo: List<ToDoItemModel>
        get() = _listOfToDo.toList()

    init {
        _listOfToDo.addAll(listHardCoded)
    }

    override fun getToDoById(id: String?): Flow<Resource<ToDoItemModel?>> = flow {
        try {
            emit(Resource.Loading())
            val data = _listOfToDo.firstOrNull { it.id == id }
            emit(Resource.Success(data))
        } catch (ex: Exception) {
            emit(Resource.Error(ex.message.toString()))
        }
    }

    override fun getAllToDo(): Flow<Resource<List<ToDoItemModel>>> = flow {
        try {
            emit(Resource.Loading())
            val data = listOfToDo
            emit(Resource.Success(data))
        } catch (ex: Exception) {
            emit(Resource.Error(ex.message.toString()))
        }
    }

    override fun addOrEditToDo(item: ToDoItemModel): Flow<Resource<Nothing>> = flow {
        try {
            emit(Resource.Loading())
            if (item.id != null) {
                _listOfToDo.replaceAll { if (it.id == item.id) item else it }
            } else {
                _listOfToDo.add(0, item.copy(id = Calendar.getInstance().time.toString()))
            }
            emit(Resource.Success())
        } catch (ex: Exception) {
            emit(Resource.Error(ex.message.toString()))
        }
    }

    override fun deleteToDo(item: ToDoItemModel): Flow<Resource<Nothing>> = flow {
        try {
            emit(Resource.Loading())
            _listOfToDo.remove(item)
            emit(Resource.Success())
        } catch (ex: Exception) {
            emit(Resource.Error(ex.message.toString()))
        }
    }

    override suspend fun countOfCompleted() = listOfToDo.count { it.completed }


}
