package by.g_alex.ysmd_todo_compose.data.remote

import by.g_alex.ysmd_todo_compose.data.remote.dto.AddOrUpdateElementByIdDto
import by.g_alex.ysmd_todo_compose.data.remote.dto.GetAllToDoListDto
import by.g_alex.ysmd_todo_compose.data.remote.dto.GetToDoByIdResponseDto
import by.g_alex.ysmd_todo_compose.data.remote.dto.PatchAllToDoRequestDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ToDoAPI {

    /** Get All Todos. Return list [GetAllToDoListDto] **/
    @GET("list")
    suspend fun getAllToDo(): GetAllToDoListDto

    /** Get To Do by [id]. Return item [GetToDoByIdResponseDto] **/
    @GET("list/{id}")
    suspend fun getToDoById(
        @Path("id") id: String
    ): GetToDoByIdResponseDto

    /** Patch list of ToDos. Return new list [GetAllToDoListDto] **/
    @PATCH("list")
    suspend fun patchAllToDos(
        @Body item: PatchAllToDoRequestDto
    ): GetAllToDoListDto

    /** Add NEW item to list. Return new item [GetToDoByIdResponseDto]  **/
    @POST("list")
    suspend fun addNewToDo(
        @Body item: AddOrUpdateElementByIdDto
    ): GetToDoByIdResponseDto

    /** Edit item to list. Return updated item [GetToDoByIdResponseDto]  **/
    @PUT("list/{id}")
    suspend fun updateToDo(
        @Path("id") id: String,
        @Body item: AddOrUpdateElementByIdDto
    ): GetToDoByIdResponseDto


    /** Delete item from list. Return deleted item [GetToDoByIdResponseDto]  **/
    @DELETE("list/{id}")
    suspend fun deleteToDo(
        @Path("id") id: String
    ): GetToDoByIdResponseDto

}