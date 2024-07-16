package by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo.additions

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.presentation.todo.components.bounceClick
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.YSMDToDoComposeTheme

@Composable
fun DeleteToDo(
    enabled: Boolean,
    onDelete: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(ToDoTheme.colors.backPrimary)
            .padding(vertical = ToDoTheme.dp.listVerticalPadding),
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(
            onClick = { onDelete()  },
            modifier = Modifier.bounceClick(),
            enabled = enabled,
            colors = ButtonDefaults.textButtonColors().copy(
                contentColor = ToDoTheme.colors.colorRed,
                disabledContentColor = ToDoTheme.colors.labelDisable
            )
        ) {
            Icon(Icons.Filled.Delete, null)
            Text(text = stringResource(R.string.todo_delete))
        }
    }
}


@Composable
@Preview(name = "Delete | Light | Can't delete")
private fun DeleteToDoPreviewLight() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            DeleteToDo(false) {}
        }
    }
}

@Composable
@Preview(name = "Delete | Dark | Can't delete", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun DeleteToDoPreviewDark() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            DeleteToDo(false) {}
        }
    }
}

@Composable
@Preview(name = "Delete | Light | Can delete")
private fun DeleteToDoPreviewLightCanSave() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            DeleteToDo(true) {}
        }
    }
}

@Composable
@Preview(name = "Delete | Dark | Can delete", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun DeleteToDoPreviewDarkCanSave() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            DeleteToDo(true) {}
        }
    }
}