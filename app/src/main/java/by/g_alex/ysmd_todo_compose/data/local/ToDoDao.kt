package by.g_alex.ysmd_todo_compose.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import by.g_alex.ysmd_todo_compose.data.local.entities.ToDoEntity

@Dao
interface ToDoDao {

    @Query("SELECT * FROM ToDoEntity")
    suspend fun getAllToDo(): List<ToDoEntity>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addToDo(item: ToDoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun editToDo(item: ToDoEntity)

    @Query("DELETE FROM ToDoEntity WHERE `id`=:id")
    suspend fun deleteToDo(id: String)

}