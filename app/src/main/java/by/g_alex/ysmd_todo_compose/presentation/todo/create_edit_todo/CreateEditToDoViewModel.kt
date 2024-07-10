package by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.ysmd_todo_compose.common.Resource
import by.g_alex.ysmd_todo_compose.common.errors.ConnectionError
import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority
import by.g_alex.ysmd_todo_compose.domain.model.ToDoItemModel
import by.g_alex.ysmd_todo_compose.domain.use_case.AddToDoUseCase
import by.g_alex.ysmd_todo_compose.domain.use_case.DeleteToDoUseCase
import by.g_alex.ysmd_todo_compose.domain.use_case.EditToDoUseCase
import by.g_alex.ysmd_todo_compose.domain.use_case.GetToDoByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CreateEditToDoViewModel @Inject constructor(
    private val getToDoByIdUseCase: GetToDoByIdUseCase,
    private val addToDoUseCase: AddToDoUseCase,
    private val deleteToDoUseCase: DeleteToDoUseCase,
    private val editToDoUseCase: EditToDoUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CreateEditToDoState())
    val state = _state.asStateFlow()

    val toDoText = mutableStateOf("")
    val selectedPriority = mutableStateOf(ToDoPriority.NONE)
    val selectedDeadline = mutableStateOf<Date?>(null)

    fun getElementById(id: String) {
        if (id != "null")
            getToDoByIdUseCase(id).onEach { res ->
                when (res) {
                    is Resource.Success -> {
                        val item = res.data

                        item?.apply {
                            toDoText.value = text
                            selectedPriority.value = priority
                            selectedDeadline.value = deadline
                        }

                        _state.update {
                            it.copy(
                                isLoading = false,
                                item = item,
                                isNetworkError = null,
                                isError = null
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

    fun saveToDo(onSuccess: () -> Unit) {
        val item = ToDoItemModel(
            state.value.item?.id,
            text = toDoText.value,
            priority = selectedPriority.value,
            deadline = selectedDeadline.value,
            completed = state.value.item?.completed ?: false
        )
        (if (item.id == null) addToDoUseCase(item) else editToDoUseCase(item)).onEach { res ->
            when (res) {
                is Resource.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    onSuccess()
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

    fun deleteToDo(onSuccess: () -> Unit) {
        deleteToDoUseCase(state.value.item?.id!!).onEach { res ->
            when (res) {
                is Resource.Success -> {
                    _state.update { it.copy(isLoading = false) }
                    onSuccess()
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
}