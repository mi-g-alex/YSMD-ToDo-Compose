package by.g_alex.ysmd_todo_compose.presentation.todo.components

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme

@Composable
fun ErrorDialog(
    onDismiss: () -> Unit,
    @StringRes errId: Int,

    ) {
    AlertDialog(
        containerColor = ToDoTheme.colors.backSecondary,
        onDismissRequest = { onDismiss() },
        text = {
            Text(
                stringResource(errId),
                color = ToDoTheme.colors.labelPrimary,
                style = ToDoTheme.typography.body
            )
        },
        confirmButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    stringResource(R.string.error_ok),
                    color = ToDoTheme.colors.colorBlue,
                    style = ToDoTheme.typography.button
                )
            }
        },
        title = {
            Text(
                stringResource(R.string.error_title),
                color = ToDoTheme.colors.labelPrimary,
                style = ToDoTheme.typography.subTitle
            )
        },

        )
}