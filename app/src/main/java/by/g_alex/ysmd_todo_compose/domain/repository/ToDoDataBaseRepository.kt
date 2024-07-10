package by.g_alex.ysmd_todo_compose.domain.repository

import by.g_alex.ysmd_todo_compose.data.remote.dto.AddOrUpdateElementByIdDto
import by.g_alex.ysmd_todo_compose.domain.model.ToDoItemModel

interface ToDoDataBaseRepository {

    /**
     * Get all ToDos
     * @return List<[ToDoItemModel]>
     * **/
    suspend fun getAllToDo() : List<ToDoItemModel>

    /**
     * Add new To Do
     * @param item new item
     * **/
    suspend fun addToDo(item: ToDoItemModel)

    /**
     * Delete To Do
     * @param item item to delete
     * **/
    suspend fun deleteToDo(item: ToDoItemModel)

    /**
     * Update To Do
     * @param id id of to do
     * @param item updated item
     * **/
    suspend fun updateToDo(id: String, item: ToDoItemModel)

}