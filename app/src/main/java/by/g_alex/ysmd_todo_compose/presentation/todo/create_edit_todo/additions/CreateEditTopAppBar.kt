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
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.YSMDToDoComposeTheme

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
                    imageVector = Icons.Outlined.Close,
                    contentDescription = stringResource(R.string.todo_go_back),
                    tint = ToDoTheme.colors.labelPrimary
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
                    contentColor = ToDoTheme.colors.colorBlue,
                    disabledContentColor = ToDoTheme.colors.colorBlue.copy(alpha = 0.6f),
                )
            ) {
                Text(
                    text = stringResource(R.string.todo_edit_save),
                    style = ToDoTheme.typography.topBarButton
                )
            }
        },
    )
}

@Composable
@Preview(name = "Edit Top Bar | Light | Can't save")
private fun CreateEditTopAppBarPreviewLight() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            CreateEditTopAppBar({}, {}, false)
        }
    }
}

@Composable
@Preview(name = "Edit Top Bar | Dark | Can't save", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun CreateEditTopAppBarPreviewDark() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            CreateEditTopAppBar({}, {}, false)
        }
    }
}

@Composable
@Preview(name = "Edit Top Bar | Light | Can save")
private fun CreateEditTopAppBarPreviewLightCanSave() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            CreateEditTopAppBar({}, {}, true)
        }
    }
}

@Composable
@Preview(name = "Edit Top Bar | Dark | Can save", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun CreateEditTopAppBarPreviewDarkCanSave() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            CreateEditTopAppBar({}, {}, true)
        }
    }
}