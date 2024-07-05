package by.g_alex.ysmd_todo_compose.common.errors

import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.common.BaseError

/** Error when token is failed **/
class AuthError: BaseError {
    override val errorTextId: Int
        get() = R.string.error_auth

}