package by.g_alex.ysmd_todo_compose.common

import androidx.annotation.StringRes


interface BaseError {
    @get:StringRes
    val errorTextId: Int
}