package by.g_alex.ysmd_todo_compose.domain.use_case.theme_setting

import by.g_alex.ysmd_todo_compose.di.AppModule
import javax.inject.Inject

class GetThemePreferenceUseCase @Inject constructor(
    private val sharedPrefs: AppModule.MyPreference
) {
    operator fun invoke(): ThemeEnum = themeEnumFromMyString(sharedPrefs.getTheme())
}