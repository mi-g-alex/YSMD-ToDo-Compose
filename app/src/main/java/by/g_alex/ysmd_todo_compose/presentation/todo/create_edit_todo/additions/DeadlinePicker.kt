package by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo.additions

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.util.Log
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.CustomTheme
import java.text.SimpleDateFormat
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

    val getTime: (date: Date) -> String = { it ->
        SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(it)
    }

    var date by remember { selectedDate }

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
            initialDisplayedMonthMillis = selectedDate.value?.time ?: cal.timeInMillis,
            initialDisplayMode = DisplayMode.Picker,
            initialSelectedDateMillis = selectedDate.value?.time ?: cal.timeInMillis,
            locale = Locale.getDefault()
        )
    }

    var calIsView by rememberSaveable { mutableStateOf(false) }

    var checked by rememberSaveable { mutableStateOf(selectedDate.value != null) }

    LaunchedEffect(date) {
        dateText = if (date != null) getTime(date!!) else ""
        selectDate(date)
        checked = date != null
    }


    LaunchedEffect(checked) {
        if (checked && date == null) {
            date = Calendar.getInstance().time
            calIsView = true
        }
        if(!checked) date = null
    }

    Row(
        Modifier
            .fillMaxWidth()
            .clickable { if (!checked) checked = true;calIsView = true }
            .background(CustomTheme.colors.backPrimary)
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

    val dpColors = DatePickerDefaults.colors(
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
        navigationContentColor = CustomTheme.colors.labelSecondary,
        todayContentColor = CustomTheme.colors.labelPrimary
    )

    if (calIsView) DatePickerDialog(
        onDismissRequest = { calIsView = false },
        confirmButton = {
            TextButton(
                {
                    date = datePickerState.selectedDateMillis?.let { Date(it) }
                    calIsView = false
                }) {
                Text("Apply", color = CustomTheme.colors.colorBlue)
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
fun DeadlinePickerPreviewLight() {
    CustomTheme {
        DeadlinePicker(mutableStateOf(null)) {}
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(name = "Deadline picker | Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun DeadlinePickerPreviewDark() {
    CustomTheme {
        DeadlinePicker(mutableStateOf(null)) {}
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(name = "Deadline picker | Light | Date")
fun DeadlinePickerPreviewLightWithDate() {
    CustomTheme {
        DeadlinePicker(mutableStateOf(Calendar.getInstance().time)) {}
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
@Preview(name = "Deadline picker | Dark | Date", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun DeadlinePickerPreviewDarkWithDate() {
    CustomTheme {
        DeadlinePicker(mutableStateOf(Calendar.getInstance().time)) {}
    }
}
