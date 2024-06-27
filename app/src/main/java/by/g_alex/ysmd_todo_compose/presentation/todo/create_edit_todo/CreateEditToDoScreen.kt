package by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority
import by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo.additions.CreateEditTextField
import by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo.additions.CreateEditTopAppBar
import by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo.additions.DeadlinePicker
import by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo.additions.DeleteToDo
import by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo.additions.PrioritySelector
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.CustomTheme
import java.util.Calendar
import java.util.Date

@Composable
fun CreateEditToDoScreen(
    id: String?,
    goBack: () -> Unit,
    viewModel: CreateEditToDoViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.getElementById(id)
    }

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

//    LaunchedEffect(selectedDeadline) {
//        Log.d("DATE", "Selected: $selectedDeadline")
//    }

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
        modifier = Modifier.background(CustomTheme.colors.backPrimary)
    ) { pad ->

        LazyColumn(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
                .background(CustomTheme.colors.backPrimary)
                .padding(horizontal = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 4.dp)
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
fun PreviewCreate() {
    CustomTheme {
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
fun PreviewCreateNight() {
    CustomTheme {
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
fun PreviewEditLight() {
    CustomTheme {
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
fun PreviewEditNight() {
    CustomTheme {
        CreateEditContent(
            true,
            mutableStateOf("Hello"),
            mutableStateOf(ToDoPriority.HIGH),
            mutableStateOf(Calendar.getInstance().time),
            {}, {}, {},
        )
    }
}
