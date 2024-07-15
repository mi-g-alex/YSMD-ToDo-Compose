package by.g_alex.ysmd_todo_compose.data.additional.enums

import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority.HIGH
import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority.LOW
import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority.NONE

enum class ToDoPriority {
    NONE,
    HIGH,
    LOW;

    companion object {
        fun fromString(str: String): ToDoPriority {
            return when (str.lowercase()) {
                "low" -> LOW
                "important" -> HIGH
                "basic" -> NONE
                else -> NONE
            }
        }
    }
}

fun ToDoPriority.toDtoString(): String {
    return when (this) {
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

