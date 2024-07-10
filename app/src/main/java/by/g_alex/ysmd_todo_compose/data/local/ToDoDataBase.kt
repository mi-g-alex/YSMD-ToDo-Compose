package by.g_alex.ysmd_todo_compose.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import by.g_alex.ysmd_todo_compose.data.local.entities.ToDoEntity

@Database(
    version = 1,
    entities = [ToDoEntity::class],
    exportSchema = true,
    autoMigrations = []
)
abstract class ToDoDataBase : RoomDatabase() {

    abstract val toDoDao: ToDoDao

    companion object {
        const val DATABASE_NAME = "todo_db"
    }

}
