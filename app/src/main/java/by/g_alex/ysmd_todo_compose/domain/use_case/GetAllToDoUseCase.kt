package by.g_alex.ysmd_todo_compose.domain.use_case

import by.g_alex.ysmd_todo_compose.common.Resource
import by.g_alex.ysmd_todo_compose.common.errors.ConnectionError
import by.g_alex.ysmd_todo_compose.common.errors.UnknownError
import by.g_alex.ysmd_todo_compose.common.errors.getErrorByHtmlCode
import by.g_alex.ysmd_todo_compose.data.remote.dto.PatchAllToDoRequestDto
import by.g_alex.ysmd_todo_compose.di.AppModule
import by.g_alex.ysmd_todo_compose.domain.model.ToDoItemModel
import by.g_alex.ysmd_todo_compose.domain.repository.ToDoApiRepository
import by.g_alex.ysmd_todo_compose.domain.repository.ToDoDataBaseRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllToDoUseCase @Inject constructor(
    private val api: ToDoApiRepository,
    private val db: ToDoDataBaseRepository,
    private val mp: AppModule.MyPreference
) {

    /**  UseCase get all to dos by id **/
    operator fun invoke(): Flow<Resource<List<ToDoItemModel>>> = flow {
        try {
            emit(Resource.Loading())
            val dbTasks = db.getAllToDo()

            emit(Resource.Success(dbTasks))
            emit(Resource.Loading())

            var res = api.getAllToDo()
            var remoteRevision = res.revision

            val localRevision = mp.getRevision()
            val hasLocalChanges = mp.getHasOfflineChanges()

            if (remoteRevision == localRevision) {
                if (hasLocalChanges) {
                    try {
                        res = api.patchAllToDos(PatchAllToDoRequestDto(dbTasks.map { it.toDto() }))
                        remoteRevision = res.revision
                        db.updateAllToDos(res.list.map { it.toModel() })
                    } catch (ex: Exception) {
                        emit(Resource.Success(dbTasks))
                        throw ex
                    }
                }
            } else {
                db.updateAllToDos(res.list.map { it.toModel() })
            }

            mp.setRevision(remoteRevision)
            mp.setHasOfflineChanges(false)

            emit(Resource.Success(res.list.map { it.toModel() }))
        } catch (e: IOException) {
            emit(Resource.Error(ConnectionError()))
        } catch (ex: HttpException) {
            emit(Resource.Error(getErrorByHtmlCode(ex.code())))
        } catch (ex: Exception) {
            emit(Resource.Error(UnknownError()))
        }
    }

}