package by.g_alex.ysmd_todo_compose.data.repository

import android.util.Log
import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority
import by.g_alex.ysmd_todo_compose.data.model.ToDoItemModel
import java.util.Calendar
import java.util.Date

class ToDoRepository {

    private val listOfToDo = mutableListOf<ToDoItemModel>()

    init {
        listOfToDo.apply {
            add(
                ToDoItemModel(
                    id = "0",
                    text = "All completed",
                    priority = ToDoPriority.HIGH,
                    completed = true,
                    deadline = null,
                    updateDate = null
                )
            )
            add(
                ToDoItemModel(
                    id = "1",
                    text = "It is first note",
                    priority = ToDoPriority.NONE,
                    completed = false,
                    deadline = null,
                    updateDate = null
                )
            )
            add(
                ToDoItemModel(
                    id = "2",
                    text = "It is second note",
                    priority = ToDoPriority.HIGH,
                    completed = true,
                    deadline = Date(1734775200000),
                    updateDate = null
                )
            )
            add(
                ToDoItemModel(
                    id = "3",
                    text = "It is third note",
                    priority = ToDoPriority.LOW,
                    completed = true,
                    deadline = Date(1724774200000),
                    updateDate = Date(1734774190000)
                )
            )
            add(
                ToDoItemModel(
                    id = "4",
                    text = "A lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot text",
                    priority = ToDoPriority.HIGH,
                    completed = false,
                    deadline = Date(1734734200000),
                    updateDate = Date(1734774190000)
                )
            )
            add(
                ToDoItemModel(
                    id = "5",
                    text = "A lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot text",
                    priority = ToDoPriority.HIGH,
                    completed = true,
                    deadline = Date(1734734200000),
                    updateDate = Date(1734774190000)
                )
            )
            add(
                ToDoItemModel(
                    id = "6",
                    text = "A lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot text",
                    priority = ToDoPriority.HIGH,
                    completed = true,
                    deadline = null,
                )
            )
            add(
                ToDoItemModel(
                    id = "7",
                    text = "What next?",
                    priority = ToDoPriority.LOW,
                    completed = false,
                    updateDate = null
                )
            )
            add(
                ToDoItemModel(
                    id = "8",
                    text = "Okey...",
                    priority = ToDoPriority.LOW,
                    completed = false,
                    updateDate = null
                )
            )
            add(
                ToDoItemModel(
                    id = "9",
                    text = "Read book | Kotlin",
                    priority = ToDoPriority.LOW,
                    completed = false,
                    deadline = Date(1834734200000),
                    updateDate = null
                )
            )
            add(
                ToDoItemModel(
                    id = "10",
                    text = "Read book | C++ on backend",
                    priority = ToDoPriority.LOW,
                    completed = false,
                    deadline = Date(1834734200000),
                    updateDate = null
                )
            )
        }
    }

    fun getToDoById(id: String?) = listOfToDo.firstOrNull { it.id == id }

    fun getAllToDo() = listOfToDo.toList()

    fun addOrEditToDo(item: ToDoItemModel) {
        if (item.id != null) {
            listOfToDo.replaceAll { if (it.id == item.id) item else it }
        } else {
            listOfToDo.add(0, item.copy(id = Calendar.getInstance().time.toString()))

        }
    }

    fun deleteToDo(item: ToDoItemModel) {
        listOfToDo.remove(item)
    }

    fun countOfCompleted() = listOfToDo.count { it.completed }


}
