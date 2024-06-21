package by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority
import by.g_alex.ysmd_todo_compose.data.model.ToDoItemModel
import by.g_alex.ysmd_todo_compose.data.repository.ToDoRepository
import by.g_alex.ysmd_todo_compose.presentation.todo.components.IconBeforeText
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.CustomTheme
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditToDoScreen(
    id: String?,
    rep: ToDoRepository,
    goBack: () -> Unit
) {

    val item = rep.getToDoById(id)

    var text by remember {
        mutableStateOf(item?.text ?: "")
    }

    var priority by remember { mutableStateOf(item?.priority ?: ToDoPriority.NONE) }

    var date by remember { mutableStateOf(item?.deadline) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton({ goBack() }) {
                        Icon(
                            Icons.Outlined.Close,
                            null,
                            tint = CustomTheme.colors.labelPrimary
                        )
                    }
                },
                actions = {
                    TextButton(
                        {
                            rep.addOrEditToDo(
                                ToDoItemModel(
                                    item?.id,
                                    text = text,
                                    priority = priority,
                                    deadline = date,
                                    completed = item?.completed ?: false
                                )
                            )
                            goBack()
                        },
                        enabled = text.isNotBlank()
                    ) {
                        Text(
                            stringResource(R.string.todo_edit_save),
                            color = CustomTheme.colors.colorBlue
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CustomTheme.colors.backPrimary,
                    titleContentColor = CustomTheme.colors.labelPrimary,
                    scrolledContainerColor = CustomTheme.colors.backPrimary,
                    actionIconContentColor = CustomTheme.colors.colorBlue,
                ),
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
                EditTextField(text) {
                    text = it
                }
            }

            item {
                PrioritySelector(priority) { priority = it }
            }

            item {
                DeadlinePicker(item?.deadline) { date = it }
            }

            item {
                DeleteToDo(item != null) {
                    rep.deleteToDo(item!!)
                    goBack()
                }
            }

        }

    }
}

@Composable
fun EditTextField(
    text: String,
    onEditText: (text: String) -> Unit
) {
    TextField(
        value = text,
        onValueChange = { onEditText(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = CustomTheme.colors.backSecondary,
            unfocusedContainerColor = CustomTheme.colors.backSecondary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = CustomTheme.colors.labelPrimary,
            unfocusedTextColor = CustomTheme.colors.labelPrimary,
            focusedLabelColor = CustomTheme.colors.labelSecondary,
            unfocusedLabelColor = CustomTheme.colors.labelSecondary,
            cursorColor = CustomTheme.colors.labelSecondary
        ),
        label = {
            Text(stringResource(R.string.todo_edit_label))
        },
        shape = RoundedCornerShape(8.dp),
    )
}

@Composable
private fun DropDownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onSelect: (selected: ToDoPriority) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismiss() },
        modifier = Modifier.background(CustomTheme.colors.backElevated)
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    stringResource(R.string.todo_priority_none),
                    color = CustomTheme.colors.labelPrimary
                )
            },
            onClick = { onSelect(ToDoPriority.NONE) },
            modifier = Modifier.background(CustomTheme.colors.backElevated),
        )
        DropdownMenuItem(
            text = {
                Text(
                    stringResource(R.string.todo_priority_high),
                    color = CustomTheme.colors.colorRed
                )
            },
            onClick = { onSelect(ToDoPriority.HIGH) },
            modifier = Modifier.background(CustomTheme.colors.backElevated),
            leadingIcon = { IconBeforeText(ToDoPriority.HIGH) }
        )
        DropdownMenuItem(
            text = {
                Text(
                    stringResource(R.string.todo_priority_low),
                    color = CustomTheme.colors.labelPrimary
                )
            },
            onClick = { onSelect(ToDoPriority.LOW) },
            modifier = Modifier.background(CustomTheme.colors.backElevated),
            leadingIcon = { IconBeforeText(ToDoPriority.LOW) },
        )
    }
}

@Composable
fun PrioritySelector(
    priority: ToDoPriority,
    selectPriority: (selected: ToDoPriority) -> Unit,
) {

    val priorityText = when (priority) {
        ToDoPriority.NONE -> stringResource(R.string.todo_priority_none)
        ToDoPriority.HIGH -> stringResource(R.string.todo_priority_high)
        ToDoPriority.LOW -> stringResource(R.string.todo_priority_low)
    }

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { expanded = true }
    ) {
        Text(text = "Priority", color = CustomTheme.colors.labelPrimary, fontSize = 16.sp)
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            IconBeforeText(priority, Modifier.size(14.dp))
            Text(text = priorityText, color = CustomTheme.colors.labelSecondary, fontSize = 14.sp)
        }
        DropDownMenu(expanded, { expanded = false }) { selectPriority(it); expanded = false }
    }


    HorizontalDivider(modifier = Modifier.fillMaxWidth())

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeadlinePicker(
    selectedDate: Date?,
    selectDate: (it: Date?) -> Unit
) {

    val getTime: (date: Date) -> String = { it ->
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(it)
    }

    var date by remember { mutableStateOf(selectedDate) }

    var dateText by remember { mutableStateOf(date?.let { getTime(it) } ?: "") }

    val cal = Calendar.getInstance().apply {
        GregorianCalendar(
            get(Calendar.YEAR),
            get(Calendar.MONTH),
            get(Calendar.DAY_OF_MONTH),
            0, 0, 0
        )
    }

    val datePickerState = remember {
        DatePickerState(
            yearRange = ((cal.get(GregorianCalendar.YEAR) - 10)..
                    (cal.get(GregorianCalendar.YEAR) + 10)),
            initialDisplayedMonthMillis = selectedDate?.time ?: cal.timeInMillis,
            initialDisplayMode = DisplayMode.Picker,
            initialSelectedDateMillis = selectedDate?.time ?: cal.timeInMillis,
            locale = Locale.getDefault()
        )
    }

    var calIsView by remember { mutableStateOf(false) }


    var checked by remember { mutableStateOf(selectedDate != null) }

    LaunchedEffect(date) {
        Log.d("DATE", date.toString())
        dateText = if (date != null) getTime(date!!) else ""
        selectDate(date)
        checked = date != null
    }


    LaunchedEffect(checked) {
        if (checked && date == null) {
            date = Calendar.getInstance().time
        }
    }

    Row(
        Modifier
            .fillMaxWidth()
            .clickable { if (!checked) checked = true;calIsView = true }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                stringResource(R.string.todo_make_to),
                color = CustomTheme.colors.labelPrimary,
                fontSize = 16.sp
            )
            if (date != null)
                Text(
                    text = dateText,
                    color = CustomTheme.colors.labelSecondary,
                    fontSize = 14.sp
                )
        }

        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
            },
            colors = SwitchDefaults.colors(
                checkedTrackColor = CustomTheme.colors.colorBlue,
                uncheckedTrackColor = CustomTheme.colors.backSecondary,
            )
        )
    }

    HorizontalDivider(modifier = Modifier.fillMaxWidth())

    if (calIsView) DatePickerDialog(
        onDismissRequest = { calIsView = false },
        confirmButton = {
            TextButton({
                date = datePickerState.selectedDateMillis?.let { Date(it) }
                calIsView = false
            }) {
                Text("Apply", color = CustomTheme.colors.colorBlue)
            }
        },
        colors = DatePickerDefaults.colors(
            containerColor = CustomTheme.colors.backSecondary,
            selectedDayContainerColor = CustomTheme.colors.colorBlue,
            dayContentColor = CustomTheme.colors.labelSecondary,
            currentYearContentColor = CustomTheme.colors.labelPrimary,
            todayDateBorderColor = CustomTheme.colors.colorBlue,
            yearContentColor = CustomTheme.colors.labelSecondary,
            selectedYearContainerColor = CustomTheme.colors.colorBlue,
            titleContentColor = CustomTheme.colors.labelSecondary,
            selectedDayContentColor = CustomTheme.colors.labelPrimary,
        )
    ) {
        DatePicker(
            state = datePickerState, showModeToggle = false,
            colors = DatePickerDefaults.colors(
                containerColor = CustomTheme.colors.backSecondary,
                selectedDayContainerColor = CustomTheme.colors.colorBlue,
                dayContentColor = CustomTheme.colors.labelSecondary,
                currentYearContentColor = CustomTheme.colors.labelPrimary,
                todayDateBorderColor = CustomTheme.colors.colorBlue,
                yearContentColor = CustomTheme.colors.labelSecondary,
                selectedYearContainerColor = CustomTheme.colors.colorBlue,
                titleContentColor = CustomTheme.colors.labelSecondary,
                selectedDayContentColor = CustomTheme.colors.labelPrimary,
                headlineContentColor = CustomTheme.colors.labelPrimary,
                weekdayContentColor = CustomTheme.colors.labelTertiary,
                navigationContentColor = CustomTheme.colors.labelSecondary
            )
        )
    }
}

@Composable
fun DeleteToDo(
    enabled: Boolean,
    onDelete: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(
            onClick = { onDelete() },
            enabled = enabled,
            colors = ButtonDefaults.textButtonColors().copy(
                contentColor = CustomTheme.colors.colorRed,
                disabledContentColor = CustomTheme.colors.labelDisable
            )
        ) {
            Icon(Icons.Filled.Delete, null)
            Text(text = stringResource(R.string.todo_delete))
        }
    }
}

@Composable
@Preview
fun PreviewEdit() {
    CustomTheme {
        CreateEditToDoScreen("1", ToDoRepository(), {})
    }
}

@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
fun PreviewEditMe() {
    CustomTheme {
        CreateEditToDoScreen("1", ToDoRepository(), {})
    }
}