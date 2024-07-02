package by.g_alex.ysmd_todo_compose.presentation.todo.todo_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.ysmd_todo_compose.common.Resource
import by.g_alex.ysmd_todo_compose.data.model.ToDoItemModel
import by.g_alex.ysmd_todo_compose.domain.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToDoListViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ToDoListState())
    val state = _state.asStateFlow()

    fun getAllToDos() {
        toDoRepository.getAllToDo().onEach { res ->
            when (res) {
                is Resource.Success -> {
                    _state.update {
                        _state.value.copy(
                            isLoading = false,
                            listOfToDo = res.data ?: emptyList(),
                            isError = null
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update { _state.value.copy(isLoading = true) }
                }

                is Resource.Error -> {
                    _state.update { _state.value.copy(isLoading = false, isError = res.message) }
                }
            }
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            val cnt = toDoRepository.countOfCompleted()
            _state.update { _state.value.copy(cnt = cnt) }
        }
    }

    fun completeToDo(completed: Boolean, item: ToDoItemModel) {
        toDoRepository.addOrEditToDo(item.copy(completed = completed)).onEach { res ->
            when (res) {
                is Resource.Success -> {
                    _state.update {
                        _state.value.copy(
                            isLoading = false,
                            isError = null
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update { _state.value.copy(isLoading = true) }
                }

                is Resource.Error -> {
                    _state.update { _state.value.copy(isLoading = false, isError = res.message) }
                }
            }
        }.launchIn(viewModelScope)
        getAllToDos()
    }

    fun deleteToDo(item: ToDoItemModel) {
        toDoRepository.deleteToDo(item = item).onEach { res ->
            when (res) {
                is Resource.Success -> {
                    _state.update {
                        _state.value.copy(
                            isLoading = false,
                            isError = null
                        )
                    }
                }

                is Resource.Loading -> {
                    _state.update { _state.value.copy(isLoading = true) }
                }

                is Resource.Error -> {
                    _state.update { _state.value.copy(isLoading = false, isError = res.message) }
                }
            }
        }.launchIn(viewModelScope)

        viewModelScope.launch {
            getAllToDos()
        }
    }


}