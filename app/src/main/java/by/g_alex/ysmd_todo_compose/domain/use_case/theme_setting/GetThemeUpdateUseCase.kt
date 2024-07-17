package by.g_alex.ysmd_todo_compose.domain.use_case.theme_setting

import by.g_alex.ysmd_todo_compose.di.AppModule
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetThemeUpdateUseCase @Inject constructor(
    private val prefs: AppModule.MyPreference
) {
    suspend operator fun invoke(): Flow<ThemeEnum> = prefs.getThemeUpdate()
}