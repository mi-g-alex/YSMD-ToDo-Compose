package by.g_alex.ysmd_todo_compose.data

import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority
import by.g_alex.ysmd_todo_compose.data.model.ToDoItemModel
import java.util.Date

val listHardCoded = listOf(
        ToDoItemModel(
            id = "0",
            text = "All completed",
            priority = ToDoPriority.HIGH,
            completed = true,
            deadline = null,
            updateDate = null
        ),
        ToDoItemModel(
            id = "1",
            text = "It is first note",
            priority = ToDoPriority.NONE,
            completed = false,
            deadline = null,
            updateDate = null
        ),
        ToDoItemModel(
            id = "2",
            text = "It is second note",
            priority = ToDoPriority.HIGH,
            completed = true,
            deadline = Date(1734775200000),
            updateDate = null
        ),
        ToDoItemModel(
            id = "3",
            text = "It is third note",
            priority = ToDoPriority.LOW,
            completed = true,
            deadline = Date(1724774200000),
            updateDate = Date(1734774190000)
        ),
        ToDoItemModel(
            id = "4",
            text = "A lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot text",
            priority = ToDoPriority.HIGH,
            completed = false,
            deadline = Date(1734734200000),
            updateDate = Date(1734774190000)
        ),
        ToDoItemModel(
            id = "5",
            text = "A lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot text",
            priority = ToDoPriority.HIGH,
            completed = true,
            deadline = Date(1734734200000),
            updateDate = Date(1734774190000)
        ),
        ToDoItemModel(
            id = "6",
            text = "A lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot text",
            priority = ToDoPriority.HIGH,
            completed = true,
            deadline = null,
        ),
        ToDoItemModel(
            id = "7",
            text = "What next?",
            priority = ToDoPriority.LOW,
            completed = false,
            updateDate = null
        ),
        ToDoItemModel(
            id = "8",
            text = "Okey...",
            priority = ToDoPriority.LOW,
            completed = false,
            updateDate = null
        ),
        ToDoItemModel(
            id = "9",
            text = "Read book | Kotlin",
            priority = ToDoPriority.LOW,
            completed = false,
            deadline = Date(1834734200000),
            updateDate = null
        ),
        ToDoItemModel(
            id = "10",
            text = "Read book | C++ on backend",
            priority = ToDoPriority.LOW,
            completed = false,
            deadline = Date(1834734200000),
            updateDate = null
        )
    )