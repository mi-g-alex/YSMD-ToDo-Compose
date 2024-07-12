package by.g_alex.ysmd_todo_compose.common.errors

import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.common.BaseError

/** Other errors **/
class UnknownError: BaseError {
    override val errorTextId: Int
        get() = R.string.error_unknown
}