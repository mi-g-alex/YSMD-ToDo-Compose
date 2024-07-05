package by.g_alex.ysmd_todo_compose.common.errors

import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.common.BaseError

/** Error when no internet **/
class ConnectionError : BaseError {
    override val errorTextId: Int
        get() = R.string.error_connection
}