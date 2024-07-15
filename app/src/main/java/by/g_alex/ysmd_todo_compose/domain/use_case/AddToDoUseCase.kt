package by.g_alex.ysmd_todo_compose.domain.use_case

import by.g_alex.ysmd_todo_compose.common.Resource
import by.g_alex.ysmd_todo_compose.common.errors.ConnectionError
import by.g_alex.ysmd_todo_compose.common.errors.UnknownError
import by.g_alex.ysmd_todo_compose.common.errors.getErrorByHtmlCode
import by.g_alex.ysmd_todo_compose.data.remote.dto.AddOrUpdateElementByIdDto
import by.g_alex.ysmd_todo_compose.di.AppModule
import by.g_alex.ysmd_todo_compose.domain.model.ToDoItemModel
import by.g_alex.ysmd_todo_compose.domain.repository.ToDoApiRepository
import by.g_alex.ysmd_todo_compose.domain.repository.ToDoDataBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.util.Calendar
import javax.inject.Inject

class AddToDoUseCase @Inject constructor(
    private val api: ToDoApiRepository,
    private val db: ToDoDataBaseRepository,
    private val mp: AppModule.MyPreference
) {

    /**  UseCase to add to do **/
    operator fun invoke(item: ToDoItemModel): Flow<Resource<ToDoItemModel>> = flow {
        try {

            emit(Resource.Loading())

            val tItem = item.copy(
                id = Calendar.getInstance().time.time.toString(),
                createDate = Calendar.getInstance().time
            )
            mp.setHasOfflineChanges(true)

            db.addToDo(tItem)

            val resp = api.addNewToDo(AddOrUpdateElementByIdDto(tItem.toDto()))
            mp.setRevision(resp.revision)

            mp.setHasOfflineChanges(false)

            emit(Resource.Success(resp.element.toModel()))

        } catch (e: IOException) {
            emit(Resource.Error(ConnectionError()))
        } catch (ex: HttpException) {
            emit(Resource.Error(getErrorByHtmlCode(ex.code())))
        } catch (ex: Exception) {
            emit(Resource.Error(UnknownError()))
        }
        emit(Resource.Success())
    }
}