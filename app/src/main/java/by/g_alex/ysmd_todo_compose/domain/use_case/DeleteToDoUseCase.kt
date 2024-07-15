package by.g_alex.ysmd_todo_compose.domain.use_case

import android.util.Log
import by.g_alex.ysmd_todo_compose.common.Resource
import by.g_alex.ysmd_todo_compose.common.errors.AuthError
import by.g_alex.ysmd_todo_compose.common.errors.ConnectionError
import by.g_alex.ysmd_todo_compose.common.errors.InternalServerError
import by.g_alex.ysmd_todo_compose.common.errors.NotFoundError
import by.g_alex.ysmd_todo_compose.common.errors.OldDataError
import by.g_alex.ysmd_todo_compose.common.errors.UnknownError
import by.g_alex.ysmd_todo_compose.common.errors.getErrorByHtmlCode
import by.g_alex.ysmd_todo_compose.data.remote.dto.AddOrUpdateElementByIdDto
import by.g_alex.ysmd_todo_compose.data.remote.dto.PatchAllToDoRequestDto
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

class DeleteToDoUseCase @Inject constructor(
    private val api: ToDoApiRepository,
    private val db: ToDoDataBaseRepository,
    private val mp: AppModule.MyPreference
) {

    /**  UseCase to edit to do **/
    operator fun invoke(id: String): Flow<Resource<ToDoItemModel>> = flow {
        try {

            emit(Resource.Loading())
            mp.setHasOfflineChanges(true)

            db.deleteToDo(id)
            emit(Resource.Success())
            emit(Resource.Loading())

            val resp = api.deleteToDo(id)
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
    }
}