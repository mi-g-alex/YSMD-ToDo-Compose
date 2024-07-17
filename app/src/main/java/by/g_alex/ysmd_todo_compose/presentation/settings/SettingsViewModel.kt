package by.g_alex.ysmd_todo_compose.presentation.settings

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.ysmd_todo_compose.domain.use_case.theme_setting.GetThemePreferenceUseCase
import by.g_alex.ysmd_todo_compose.domain.use_case.theme_setting.PublishThemeUpdateUseCase
import by.g_alex.ysmd_todo_compose.domain.use_case.theme_setting.SaveThemePrefsUseCase
import by.g_alex.ysmd_todo_compose.domain.use_case.theme_setting.ThemeEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val getThemePreferenceUseCase: GetThemePreferenceUseCase,
    private val saveThemePrefsUseCase: SaveThemePrefsUseCase,
    private val publishThemeUpdateUseCase: PublishThemeUpdateUseCase
) : ViewModel() {

    private val _theme by lazy { mutableStateOf(getThemePreferenceUseCase()) }
    val theme: State<ThemeEnum> by lazy { _theme }

    fun setTheme(theme: ThemeEnum) {
        _theme.value = theme
        viewModelScope.launch {
            saveThemePrefsUseCase(theme)
            publishThemeUpdateUseCase(theme)
        }
    }

}