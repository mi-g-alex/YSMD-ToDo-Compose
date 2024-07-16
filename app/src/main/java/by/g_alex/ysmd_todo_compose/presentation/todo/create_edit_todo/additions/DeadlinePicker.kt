package by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo.additions

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.common.Constants.DATE_PICKER_DEFAULT_RANGE
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.YSMDToDoComposeTheme
import by.g_alex.ysmd_todo_compose.presentation.utils.dateToString
import java.util.Calendar
import java.util.Date
import java.util.GregorianCalendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeadlinePicker(
    selectedDate: MutableState<Date?>,
    selectDate: (it: Date?) -> Unit
) {

    var date by remember { selectedDate }

    var dateText by remember {
        mutableStateOf(date?.let { d ->
            dateToString(d)
        } ?: "")
    }

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
            yearRange = ((cal.get(GregorianCalendar.YEAR) - DATE_PICKER_DEFAULT_RANGE)..
                    (cal.get(GregorianCalendar.YEAR) + DATE_PICKER_DEFAULT_RANGE)),
            initialDisplayedMonthMillis = selectedDate.value?.time ?: cal.timeInMillis,
            initialDisplayMode = DisplayMode.Picker,
            initialSelectedDateMillis = selectedDate.value?.time ?: cal.timeInMillis,
            locale = Locale.getDefault()
        )
    }

    var calIsView by remember { mutableStateOf(false) }

    var checked by remember { mutableStateOf(selectedDate.value != null) }

    LaunchedEffect(date) {
        dateText = if (date != null) dateToString(date!!) else ""
        selectDate(date)
        checked = date != null
    }


    LaunchedEffect(checked) {
        if (checked && date == null) {
            date = Calendar.getInstance().time
            calIsView = true
        }
        if (!checked) date = null
    }

    Row(
        Modifier
            .fillMaxWidth()
            .clickable { if (!checked) checked = true; calIsView = true }
            .background(ToDoTheme.colors.backPrimary)
            .padding(vertical = ToDoTheme.dp.listVerticalPadding),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column {
            Text(
                stringResource(R.string.todo_make_to),
                color = ToDoTheme.colors.labelPrimary,
                style = ToDoTheme.typography.body
            )
            if (date != null)
                Text(
                    text = dateText,
                    color = ToDoTheme.colors.labelSecondary,
                    style = ToDoTheme.typography.support
                )
        }

        Switch(
            checked = checked,
            onCheckedChange = {
                checked = it
            },
            colors = SwitchDefaults.colors(
                checkedTrackColor = ToDoTheme.colors.colorBlue,
                uncheckedTrackColor = ToDoTheme.colors.backSecondary,
            )
        )
    }

    HorizontalDivider(modifier = Modifier.fillMaxWidth())

    val dpColors = DatePickerDefaults.colors(
        selectedDayContainerColor = ToDoTheme.colors.colorBlue,
        currentYearContentColor = ToDoTheme.colors.labelPrimary,
        todayDateBorderColor = ToDoTheme.colors.colorBlue,
        yearContentColor = ToDoTheme.colors.labelSecondary,
        selectedYearContainerColor = ToDoTheme.colors.colorBlue,
        todayContentColor = ToDoTheme.colors.labelPrimary
    )

    if (calIsView) DatePickerDialog(
        onDismissRequest = { calIsView = false },
        confirmButton = {
            TextButton(
                {
                    date = datePickerState.selectedDateMillis?.let { Date(it) }
                    calIsView = false
                }) {
                Text("Apply", color = ToDoTheme.colors.colorBlue)
            }
        },
        colors = dpColors
    ) {
        DatePicker(
            state = datePickerState, showModeToggle = false,
            colors = dpColors
        )
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(name = "Deadline picker | Light")
private fun DeadlinePickerPreviewLight() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            DeadlinePicker(mutableStateOf(null)) {}
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(name = "Deadline picker | Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun DeadlinePickerPreviewDark() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            DeadlinePicker(mutableStateOf(null)) {}
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(name = "Deadline picker | Light | Date")
private fun DeadlinePickerPreviewLightWithDate() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            DeadlinePicker(mutableStateOf(Calendar.getInstance().time)) {}
        }
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(name = "Deadline picker | Dark | Date", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun DeadlinePickerPreviewDarkWithDate() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            DeadlinePicker(mutableStateOf(Calendar.getInstance().time)) {}
        }
    }
}
