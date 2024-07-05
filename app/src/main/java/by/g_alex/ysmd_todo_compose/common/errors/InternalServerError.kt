package by.g_alex.ysmd_todo_compose.common.errors

import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.common.BaseError

/** Error when internal server error **/
class InternalServerError: BaseError {
    override val errorTextId: Int
        get() = R.string.error_internal_server
}