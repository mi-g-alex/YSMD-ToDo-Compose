package by.g_alex.ysmd_todo_compose.domain.use_case

import android.util.Log
import by.g_alex.ysmd_todo_compose.common.Resource
import by.g_alex.ysmd_todo_compose.common.errors.AuthError
import by.g_alex.ysmd_todo_compose.common.errors.ConnectionError
import by.g_alex.ysmd_todo_compose.common.errors.InternalServerError
import by.g_alex.ysmd_todo_compose.common.errors.NotFoundError
import by.g_alex.ysmd_todo_compose.common.errors.OldDataError
import by.g_alex.ysmd_todo_compose.common.errors.UnknownError
import by.g_alex.ysmd_todo_compose.di.AppModule
import by.g_alex.ysmd_todo_compose.domain.model.ToDoItemModel
import by.g_alex.ysmd_todo_compose.domain.repository.ToDoApiRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllToDoUseCase @Inject constructor(
    private val api: ToDoApiRepository,
    private val mp: AppModule.MyPreference
) {

    /**  UseCase get all to dos by id **/
    operator fun invoke(): Flow<Resource<List<ToDoItemModel>>> = flow {
        try {
            emit(Resource.Loading())

            val data = api.getAllToDo()
            val list = data.list.map { it.toModel() }

            mp.setRevision(data.revision)

            emit(Resource.Success(list))
        }  catch (e: IOException) {
            emit(Resource.Error(ConnectionError()))
        } catch (ex: HttpException) {
            emit(
                Resource.Error(
                    when (ex.code()) {
                        400 -> OldDataError()
                        401 -> AuthError()
                        404 -> NotFoundError()
                        500 -> InternalServerError()
                        else -> UnknownError()
                    }
                )
            )
        } catch (ex: Exception) {
            emit(Resource.Error(UnknownError()))
        }
    }

}