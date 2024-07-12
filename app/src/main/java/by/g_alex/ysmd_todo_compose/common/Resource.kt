package by.g_alex.ysmd_todo_compose.common

sealed class Resource<T>(val data: T? = null, val error: BaseError? = null) {
    class Success<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(error: BaseError, data: T? = null) : Resource<T>(data, error)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}