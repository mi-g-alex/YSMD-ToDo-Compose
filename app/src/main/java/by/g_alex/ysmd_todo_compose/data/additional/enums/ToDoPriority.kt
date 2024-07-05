package by.g_alex.ysmd_todo_compose.data.additional.enums

import by.g_alex.ysmd_todo_compose.domain.model.ToDoItemModel

enum class ToDoPriority {
    NONE,
    HIGH,
    LOW;

    companion object {
        fun fromDto(str: String): ToDoPriority {
            return when (str.lowercase()) {
                "low" -> LOW
                "important" -> HIGH
                "basic" -> NONE
                else -> NONE
            }
        }

        fun toDtoString(priority: ToDoPriority): String {
            return when (priority) {
                LOW -> {
                    "low"
                }

                NONE -> {
                    "basic"
                }

                HIGH -> {
                    "important"
                }
            }
        }
    }
}