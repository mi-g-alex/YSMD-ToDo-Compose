package by.g_alex.ysmd_todo_compose.presentation.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun dateToString(date: Date, format: String = "dd.MM.yyyy"): String =
    SimpleDateFormat(format, Locale.getDefault()).format(date)