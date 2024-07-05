package by.g_alex.ysmd_todo_compose.presentation.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun dateToString(date: Date): String =
    SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)