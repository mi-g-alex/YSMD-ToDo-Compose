package by.g_alex.ysmd_todo_compose.domain.use_case.theme_setting

import by.g_alex.ysmd_todo_compose.di.AppModule
import javax.inject.Inject

class PublishThemeUpdateUseCase @Inject constructor(
    private val sharedPrefs: AppModule.MyPreference
) {
    suspend operator fun invoke(theme: ThemeEnum) = sharedPrefs.publishThemeUpdate(theme)
}