package by.g_alex.ysmd_todo_compose.presentation.settings

import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.presentation.todo.components.bounceClick
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.YSMDToDoComposeTheme
import by.g_alex.ysmd_todo_compose.domain.use_case.theme_setting.ThemeEnum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClicked: () -> Unit,
    viewModel: SettingsViewModel = hiltViewModel()
) {

    val theme = viewModel.theme

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(stringResource(R.string.settings_title))
                },
                navigationIcon = {
                    IconButton({ onBackClicked() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.todo_go_back),
                            tint = ToDoTheme.colors.labelPrimary
                        )
                    }
                },
            )
        }
    ) { pad ->

        Column(modifier = Modifier.padding(pad)) {
            SettingElement(theme.value == ThemeEnum.Light, R.string.theme_light) {
                viewModel.setTheme(ThemeEnum.Light)
            }
            SettingElement(theme.value == ThemeEnum.Dark, R.string.theme_dark) {
                viewModel.setTheme(ThemeEnum.Dark)
            }
            SettingElement(theme.value == ThemeEnum.Auto, R.string.theme_auto) {
                viewModel.setTheme(ThemeEnum.Auto)
            }
        }

    }

}

@Composable
private fun SettingElement(
    selected: Boolean,
    @StringRes label: Int,
    onClick: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .bounceClick { onClick() }
            .padding(ToDoTheme.dp.listHorizontalPadding),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = { onClick() },
            modifier = Modifier.clickable(
                remember { MutableInteractionSource() },
                rememberRipple(false, 0.dp)
            ) {},
            colors = RadioButtonDefaults.colors().copy(
                selectedColor = ToDoTheme.colors.colorBlue,
                unselectedColor = ToDoTheme.colors.colorGray
            )
        )
        Text(stringResource(label), style = ToDoTheme.typography.body)
    }
}

@Composable
@Preview
private fun SettingElementPreview() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            SettingElement(false, R.string.theme_light) { }
        }
    }
}

@Composable
@Preview
private fun SettingElementPreviewTrue() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            SettingElement(true, R.string.theme_light) { }
        }
    }
}