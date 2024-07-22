package by.g_alex.ysmd_todo_compose.domain.use_case.theme_setting

import by.g_alex.ysmd_todo_compose.di.AppModule
import javax.inject.Inject

class SaveThemePrefsUseCase @Inject constructor(
    private val sharedPrefs: AppModule.MyPreference
) {
    operator fun invoke(theme: ThemeEnum) = sharedPrefs.setTheme(theme)
}