package by.g_alex.ysmd_todo_compose.common.workers

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import by.g_alex.ysmd_todo_compose.data.remote.ToDoAPI
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

/** Worker to update every 8 hours **/
@HiltWorker
class DataUpdateWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    @Inject
    lateinit var toDoAPI: ToDoAPI

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("WORKER", "SETTED")
                // Here only request before adding db
                toDoAPI.getAllToDo()
                Result.success()
            } catch (ex: Exception) {
                Result.retry()
            }
        }
    }
}
