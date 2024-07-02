package by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority
import by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo.additions.CreateEditTextField
import by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo.additions.CreateEditTopAppBar
import by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo.additions.DeadlinePicker
import by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo.additions.DeleteToDo
import by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo.additions.PrioritySelector
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme
import java.util.Calendar
import java.util.Date

@Composable
fun CreateEditToDoScreen(
    goBack: () -> Unit,
    viewModel: CreateEditToDoViewModel
) {

    val state = viewModel.state.collectAsState()

    val item = state.value.item

    CreateEditContent(
        canBeDeleted = item != null,
        mToDoText = viewModel.toDoText,
        mSelectedPriority = viewModel.selectedPriority,
        mSelectedDeadline = viewModel.selectedDeadline,
        saveToDo = { viewModel.saveToDo() },
        deleteToDo = { viewModel.deleteToDo() },
        goBack = goBack
    )
}

@Composable
private fun CreateEditContent(
    canBeDeleted: Boolean,
    mToDoText: MutableState<String>,
    mSelectedPriority: MutableState<ToDoPriority>,
    mSelectedDeadline: MutableState<Date?>,
    saveToDo: () -> Unit,
    deleteToDo: () -> Unit,
    goBack: () -> Unit
) {

    var toDoText by remember { mToDoText }

    var selectedPriority by remember { mSelectedPriority }

    Scaffold(
        topBar = {
            CreateEditTopAppBar(
                onBackClicked = goBack,
                onSaveClick = {
                    saveToDo()
                    goBack()
                },
                enabledSave = toDoText.isNotBlank()
            )
        },
        modifier = Modifier.background(ToDoTheme.colors.backPrimary)
    ) { pad ->

        LazyColumn(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
                .background(ToDoTheme.colors.backPrimary)
                .padding(horizontal = ToDoTheme.dp.listHorizontalPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = ToDoTheme.dp.listContentPadding)
        ) {

            item {
                CreateEditTextField(toDoText) {
                    toDoText = it
                }
            }

            item {
                PrioritySelector(selectedPriority) { selectedPriority = it }
            }

            item {
                DeadlinePicker(mSelectedDeadline) { mSelectedDeadline.value = it }
            }

            item {
                DeleteToDo(canBeDeleted) {
                    deleteToDo()
                    goBack()
                }
            }

        }

    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(name = "New | Light theme")
private fun PreviewCreate() {
    ToDoTheme {
        CreateEditContent(
            false,
            mutableStateOf(""),
            mutableStateOf(ToDoPriority.NONE),
            mutableStateOf(null),
            {}, {}, {},
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(name = "New | Dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PreviewCreateNight() {
    ToDoTheme {
        CreateEditContent(
            false,
            mutableStateOf(""),
            mutableStateOf(ToDoPriority.NONE),
            mutableStateOf(null),
            {}, {}, {},
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(name = "Edit | Light")
private fun PreviewEditLight() {
    ToDoTheme {
        CreateEditContent(
            true,
            mutableStateOf("Hello"),
            mutableStateOf(ToDoPriority.HIGH),
            mutableStateOf(Calendar.getInstance().time),
            {}, {}, {},
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(name = "Edit | Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PreviewEditNight() {
    ToDoTheme {
        CreateEditContent(
            true,
            mutableStateOf("Hello"),
            mutableStateOf(ToDoPriority.HIGH),
            mutableStateOf(Calendar.getInstance().time),
            {}, {}, {},
        )
    }
}
