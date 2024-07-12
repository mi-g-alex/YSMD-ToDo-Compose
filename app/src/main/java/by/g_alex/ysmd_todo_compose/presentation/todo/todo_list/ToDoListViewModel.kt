package by.g_alex.ysmd_todo_compose.presentation.todo.todo_list

import android.content.Context
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.util.Log
import androidx.compose.runtime.traceEventEnd
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.ysmd_todo_compose.common.Resource
import by.g_alex.ysmd_todo_compose.common.broadcast_receivers.NetworkChangeReceiver
import by.g_alex.ysmd_todo_compose.common.errors.ConnectionError
import by.g_alex.ysmd_todo_compose.di.AppModule
import by.g_alex.ysmd_todo_compose.domain.model.ToDoItemModel
import by.g_alex.ysmd_todo_compose.domain.repository.ToDoRepository
import by.g_alex.ysmd_todo_compose.domain.use_case.DeleteToDoUseCase
import by.g_alex.ysmd_todo_compose.domain.use_case.EditToDoUseCase
import by.g_alex.ysmd_todo_compose.domain.use_case.GetAllToDoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(
    private val getAllToDoUseCase: GetAllToDoUseCase,
    private val editToDoUseCase: EditToDoUseCase,
    private val deleteToDoUseCase: DeleteToDoUseCase,
    private val mp: AppModule.MyPreference,
    @ApplicationContext private val context: Context
) : ViewModel(), NetworkChangeReceiver.NetworkChangeListener {

    private val _state = MutableStateFlow(ToDoListState())
    val state = _state.asStateFlow()

    private val networkChangeReceiver = NetworkChangeReceiver(this)

    init {
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        context.registerReceiver(networkChangeReceiver, filter)
    }

    override fun onCleared() {
        super.onCleared()
        context.unregisterReceiver(networkChangeReceiver)
    }

    override fun onNetworkChanged(isConnected: Boolean) {
        Log.e("List VM", "BROADCAST, NETWORK: ${isConnected}")
        if (isConnected) {
            getAllToDos()
        } else {
            _state.update {
                it.copy(
                    isLoading = false,
                    isError = ConnectionError().errorTextId,
                    isNetworkError = true
                )
            }
        }
    }

    fun getAllToDos() {
        getAllToDoUseCase().onEach { res ->
            when (res) {
                is Resource.Success -> {
                    val cnt = res.data?.count { it.completed } ?: 0
                    _state.update {
                        it.copy(
                            isLoading = false,
                            listOfToDo = res.data ?: emptyList(),
                            cnt = cnt,
                            isError = null,
                            isNetworkError = null
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true,
                            isError = null,
                            isNetworkError = null
                        )
                    }
                }

                is Resource.Error -> {
                    val isNetwork = res.error is ConnectionError
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isError = res.error?.errorTextId,
                            isNetworkError = isNetwork
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)

    }

    fun completeToDo(completed: Boolean, item: ToDoItemModel) {
        editToDoUseCase(item.copy(completed = completed)).onEach { res ->
            when (res) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isError = null,
                            isNetworkError = null
                        )
                    }
                    getAllToDos()
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true,
                            isError = null,
                            isNetworkError = null
                        )
                    }
                }

                is Resource.Error -> {
                    val isNetwork = res.error is ConnectionError
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isError = res.error?.errorTextId,
                            isNetworkError = isNetwork
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)

    }

    fun deleteToDo(item: ToDoItemModel) {
        deleteToDoUseCase(item.id!!).onEach { res ->
            when (res) {
                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isError = null,
                            isNetworkError = null
                        )
                    }
                    getAllToDos()
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true,
                            isError = null,
                            isNetworkError = null
                        )
                    }
                }

                is Resource.Error -> {
                    val isNetwork = res.error is ConnectionError
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isError = res.error?.errorTextId,
                            isNetworkError = isNetwork
                        )
                    }
                }
            }
        }.launchIn(viewModelScope)

    }

    private fun getToken() {
        val token = mp.getToken()
        val isBearer = mp.getAuthType()
        _state.update {
            it.copy(
                authToken = token,
                isBearerToken = isBearer
            )
        }
    }

    fun saveAuthToken(token: String?, isBearer: Boolean?) {
        mp.setToken(token, isBearer)
        _state.update {
            it.copy(
                authToken = token ?: "",
                isBearerToken = isBearer
            )
        }
        getAllToDos()
    }


}