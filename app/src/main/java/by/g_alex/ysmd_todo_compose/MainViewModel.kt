package by.g_alex.ysmd_todo_compose

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.g_alex.ysmd_todo_compose.domain.use_case.theme_setting.GetThemePreferenceUseCase
import by.g_alex.ysmd_todo_compose.domain.use_case.theme_setting.GetThemeUpdateUseCase
import by.g_alex.ysmd_todo_compose.domain.use_case.theme_setting.ThemeEnum
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getThemeUpdateUseCase: GetThemeUpdateUseCase,
    private val getThemePreferenceUseCase: GetThemePreferenceUseCase
) : ViewModel() {
    private val _theme by lazy { mutableStateOf(ThemeEnum.Light) }
    val theme: State<ThemeEnum> by lazy { _theme.apply { updateTheme() } }

    init {
        viewModelScope.launch {
            getThemeUpdateUseCase().collect {
                _theme.value = it
            }
        }
    }

    private fun updateTheme() {
        _theme.value = getThemePreferenceUseCase()
    }
}