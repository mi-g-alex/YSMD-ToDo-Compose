package by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority
import by.g_alex.ysmd_todo_compose.data.model.ToDoItemModel
import by.g_alex.ysmd_todo_compose.domain.ToDoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
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
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            val item = toDoRepository.getToDoById(id)
            item?.apply {
                toDoText.value = text
                selectedPriority.value = priority
                selectedDeadline.value = deadline
            }
            _state.value = _state.value.copy(isLoading = false, item = item)
        }
    }

    fun saveToDo() {
        val item = ToDoItemModel(
            state.value.item?.id,
            text = toDoText.value,
            priority = selectedPriority.value,
            deadline = selectedDeadline.value,
            completed = state.value.item?.completed ?: false
        )
        viewModelScope.launch {
            toDoRepository.addOrEditToDo(item)
        }
    }

    fun deleteToDo() {
        viewModelScope.launch {
            state.value.item?.let {
                toDoRepository.deleteToDo(it)
            }
        }
    }
}