package by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import by.g_alex.ysmd_todo_compose.common.Resource
import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority
import by.g_alex.ysmd_todo_compose.data.model.ToDoItemModel
import by.g_alex.ysmd_todo_compose.domain.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class CreateEditToDoViewModel @Inject constructor(
    private val toDoRepository: ToDoRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CreateEditToDoState())
    val state = _state.asStateFlow()

    val toDoText = mutableStateOf("")
    val selectedPriority = mutableStateOf(ToDoPriority.NONE)
    val selectedDeadline = mutableStateOf<Date?>(null)

    fun getElementById(id: String?) {
        toDoRepository.getToDoById(id).onEach { res ->
            when (res) {
                is Resource.Success -> {
                    val item = res.data

                    item?.apply {
                        toDoText.value = text
                        selectedPriority.value = priority
                        selectedDeadline.value = deadline
                    }

                    _state.update { _state.value.copy(isLoading = false, item = item) }
                }

                is Resource.Loading -> {
                    _state.update { _state.value.copy(isLoading = true) }
                }

                is Resource.Error -> {
                    _state.update { _state.value.copy(isLoading = false, isError = res.message) }
                }
            }

        }.launchIn(viewModelScope)
    }

    fun saveToDo() {
        val item = ToDoItemModel(
            state.value.item?.id,
            text = toDoText.value,
            priority = selectedPriority.value,
            deadline = selectedDeadline.value,
            completed = state.value.item?.completed ?: false
        )
        toDoRepository.addOrEditToDo(item).onEach { res ->
            when (res) {
                is Resource.Success -> {
                    _state.update { _state.value.copy(isLoading = false) }
                }

                is Resource.Loading -> {
                    _state.update { _state.value.copy(isLoading = true) }
                }

                is Resource.Error -> {
                    _state.update { _state.value.copy(isLoading = false, isError = res.message) }
                }
            }

        }.launchIn(viewModelScope)
    }

    fun deleteToDo() {
        toDoRepository.deleteToDo(state.value.item!!).onEach { res ->
            when (res) {
                is Resource.Success -> {
                    _state.update { _state.value.copy(isLoading = false) }
                }

                is Resource.Loading -> {
                    _state.update { _state.value.copy(isLoading = true) }
                }

                is Resource.Error -> {
                    _state.update { _state.value.copy(isLoading = false, isError = res.message) }
                }
            }
        }.launchIn(viewModelScope)
    }
}