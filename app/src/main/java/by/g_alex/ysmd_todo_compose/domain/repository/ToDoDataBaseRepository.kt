package by.g_alex.ysmd_todo_compose.domain.repository

import by.g_alex.ysmd_todo_compose.domain.model.ToDoItemModel

interface ToDoDataBaseRepository {

    /**
     * Get all ToDos
     * @return List<[ToDoItemModel]>
     * **/
    suspend fun getAllToDo() : List<ToDoItemModel>

    /**
     * Delete all todos and set new
     * @param todos - list of new items
     * */
    suspend fun updateAllToDos(todos: List<ToDoItemModel>)

    /**
     * Add new To Do
     * @param item new item
     * **/
    suspend fun addToDo(item: ToDoItemModel)

    /**
     * Delete To Do
     * @param id id of item to delete
     * **/
    suspend fun deleteToDo(id: String)

    /**
     * Update To Do
     * @param id id of to do
     * @param item updated item
     * **/
    suspend fun updateToDo(id: String, item: ToDoItemModel)

}