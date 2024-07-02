package by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo.additions

import android.content.res.Configuration
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.CustomTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditTopAppBar(
    onBackClicked: () -> Unit,
    onSaveClick: () -> Unit,
    enabledSave: Boolean
) {
    TopAppBar(
        title = {},
        navigationIcon = {
            IconButton({ onBackClicked() }) {
                Icon(
                    Icons.Outlined.Close,
                    null,
                    tint = CustomTheme.colors.labelPrimary
                )
            }
        },
        actions = {
            TextButton(
                {
                    onSaveClick()
                },
                enabled = enabledSave,
                colors = ButtonDefaults.textButtonColors().copy(
                    contentColor = CustomTheme.colors.colorBlue,
                    disabledContentColor = CustomTheme.colors.colorBlue.copy(alpha = 0.6f),
                )
            ) {
                Text(stringResource(R.string.todo_edit_save))
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = CustomTheme.colors.backPrimary,
            titleContentColor = CustomTheme.colors.labelPrimary,
            scrolledContainerColor = CustomTheme.colors.backPrimary,
            actionIconContentColor = CustomTheme.colors.colorBlue,
        ),
    )
}

@Composable
@Preview(name = "Edit Top Bar | Light | Can't save")
fun CreateEditTopAppBarPreviewLight() {
    CustomTheme {
        CreateEditTopAppBar({}, {}, false)
    }
}

@Composable
@Preview(name = "Edit Top Bar | Dark | Can't save", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun CreateEditTopAppBarPreviewDark() {
    CustomTheme {
        CreateEditTopAppBar({}, {}, false)
    }
}

@Composable
@Preview(name = "Edit Top Bar | Light | Can save")
fun CreateEditTopAppBarPreviewLightCanSave() {
    CustomTheme {
        CreateEditTopAppBar({}, {}, true)
    }
}

@Composable
@Preview(name = "Edit Top Bar | Dark | Can save", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun CreateEditTopAppBarPreviewDarkCanSave() {
    CustomTheme {
        CreateEditTopAppBar({}, {}, true)
    }
}