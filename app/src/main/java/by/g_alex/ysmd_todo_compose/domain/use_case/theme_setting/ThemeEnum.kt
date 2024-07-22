package by.g_alex.ysmd_todo_compose.domain.use_case.theme_setting

import kotlinx.coroutines.flow.MutableSharedFlow

internal val themeUpdateFlow = MutableSharedFlow<ThemeEnum>()

enum class ThemeEnum { Light, Dark, Auto }

fun ThemeEnum.toMyString() = when (this) {
    ThemeEnum.Light -> "light"
    ThemeEnum.Dark -> "dark"
    ThemeEnum.Auto -> "auto"
}

fun themeEnumFromMyString(it: String) = when (it) {
    "light" -> ThemeEnum.Light
    "dark" -> ThemeEnum.Dark
    "auto" -> ThemeEnum.Auto
    else -> ThemeEnum.Auto
}


