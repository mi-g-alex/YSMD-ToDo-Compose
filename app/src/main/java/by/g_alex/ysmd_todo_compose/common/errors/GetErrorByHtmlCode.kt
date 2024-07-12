package by.g_alex.ysmd_todo_compose.common.errors

/**
 * @param code 400 - Revision Error
 * @param code 401 - Auth Error
 * @param code 404 - Element not found
 * @param code 500 - Server Error
 * @param code _else_ - Unknown Error
 */
fun getErrorByHtmlCode(code: Int) = when (code) {
    400 -> OldDataError()
    401 -> AuthError()
    404 -> NotFoundError()
    500 -> InternalServerError()
    else -> UnknownError()
}