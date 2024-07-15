package by.g_alex.ysmd_todo_compose.presentation.todo.todo_list

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.common.Resource
import by.g_alex.ysmd_todo_compose.common.errors.ConnectionError
import by.g_alex.ysmd_todo_compose.di.AppModule
import by.g_alex.ysmd_todo_compose.di.NetworkModule
import by.g_alex.ysmd_todo_compose.domain.model.ToDoItemModel
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
    private val networkAvailable: NetworkModule.NetworkState
) : ViewModel() {

    private val _state = MutableStateFlow(ToDoListState())
    val state = _state.asStateFlow()

    init {
        getToken()
        checkNetwork()
    }

    private fun checkNetwork() {
        networkAvailable.getConnectivityAsFlow().onEach { available ->
            Log.e("NETWORK", "Changed available to $available")
            _state.update {
                it.copy(isError = R.string.error_connection)
            }
            if(available) getAllToDos()
        }.launchIn(viewModelScope)
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
                            isError = null
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true,
                            isError = null
                        )
                    }
                }

                is Resource.Error -> {
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isError = res.error?.errorTextId
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
                        )
                    }
                    getAllToDos()
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true,
                            isError = null,
                        )
                    }
                }

                is Resource.Error -> {
                    val isNetwork = res.error is ConnectionError
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isError = res.error?.errorTextId,
                            isNetworkAvailble = isNetwork
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
                        )
                    }
                    getAllToDos()
                }

                is Resource.Loading -> {
                    _state.update {
                        it.copy(
                            isLoading = true,
                            isError = null,
                        )
                    }
                }

                is Resource.Error -> {
                    val isNetwork = res.error is ConnectionError
                    _state.update {
                        it.copy(
                            isLoading = false,
                            isError = res.error?.errorTextId,
                            isNetworkAvailble = isNetwork
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