package by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority
import by.g_alex.ysmd_todo_compose.presentation.todo.components.ErrorDialog
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
        saveToDo = { viewModel.saveToDo { goBack() } },
        deleteToDo = { viewModel.deleteToDo { goBack() } },
        error = state.value.isError,
        goBack = goBack
    )
}

@Composable
private fun CreateEditContent(
    canBeDeleted: Boolean,
    mToDoText: MutableState<String>,
    mSelectedPriority: MutableState<ToDoPriority>,
    @StringRes error: Int?,
    mSelectedDeadline: MutableState<Date?>,
    saveToDo: () -> Unit,
    deleteToDo: () -> Unit,
    goBack: () -> Unit
) {

    var toDoText by remember { mToDoText }

    var selectedPriority by remember { mSelectedPriority }

    var showErrorDialog by remember { mutableStateOf(false) }

    LaunchedEffect(error) {
        if (error != null) showErrorDialog = true
    }

    Scaffold(
        topBar = {
            CreateEditTopAppBar(
                onBackClicked = goBack,
                onSaveClick = saveToDo,
                enabledSave = toDoText.isNotBlank()
            )
        },
        modifier = Modifier.background(ToDoTheme.colors.backPrimary)
    ) { pad ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .background(ToDoTheme.colors.backPrimary)
        ) {

            LazyColumn(
                modifier = Modifier
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

            if(error != null)
            Text(
                stringResource(error),
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(ToDoTheme.colors.colorRed),
                color = Color.White,
                style = ToDoTheme.typography.support,
                textAlign = TextAlign.Center
            )
        }


    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(name = "New | Light theme")
private fun PreviewCreate() {
    ToDoTheme {
        CreateEditContent(
            canBeDeleted = false,
            mToDoText = mutableStateOf(""),
            mSelectedPriority = mutableStateOf(ToDoPriority.NONE),
            error = 0,
            mSelectedDeadline = mutableStateOf(null),
            saveToDo = {}, deleteToDo = {}, goBack = {},
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(name = "New | Dark theme", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PreviewCreateNight() {
    ToDoTheme {
        CreateEditContent(
            canBeDeleted = false,
            mToDoText = mutableStateOf(""),
            mSelectedPriority = mutableStateOf(ToDoPriority.NONE),
            error = 0,
            mSelectedDeadline = mutableStateOf(null),
            saveToDo = {}, deleteToDo = {}, goBack = {},
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(name = "Edit | Light")
private fun PreviewEditLight() {
    ToDoTheme {
        CreateEditContent(
            canBeDeleted = true,
            mToDoText = mutableStateOf("Hello"),
            mSelectedPriority = mutableStateOf(ToDoPriority.HIGH),
            error = 0,
            mSelectedDeadline = mutableStateOf(Calendar.getInstance().time),
            saveToDo = {}, deleteToDo = {}, goBack = {},
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(name = "Edit | Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PreviewEditNight() {
    ToDoTheme {
        CreateEditContent(
            canBeDeleted = true,
            mToDoText = mutableStateOf("Hello"),
            mSelectedPriority = mutableStateOf(ToDoPriority.HIGH),
            error = 0,
            mSelectedDeadline = mutableStateOf(Calendar.getInstance().time),
            saveToDo = {}, deleteToDo = {}, goBack = {},
        )
    }
}
