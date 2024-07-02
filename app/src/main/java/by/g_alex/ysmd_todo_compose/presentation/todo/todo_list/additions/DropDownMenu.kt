package by.g_alex.ysmd_todo_compose.presentation.todo.todo_list.additions

import androidx.compose.foundation.background
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme

@Composable
internal fun DropDownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismiss() },
        modifier = Modifier.background(ToDoTheme.colors.backElevated)
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    stringResource(R.string.todo_edit),
                    color = ToDoTheme.colors.labelPrimary,
                    style = ToDoTheme.typography.support
                )
            },
            onClick = { onEdit() },
            modifier = Modifier.background(ToDoTheme.colors.backElevated),
        )
        DropdownMenuItem(
            text = {
                Text(
                    stringResource(R.string.todo_delete),
                    color = ToDoTheme.colors.colorRed,
                    style = ToDoTheme.typography.support
                )
            },
            onClick = { onDelete() },
            modifier = Modifier.background(ToDoTheme.colors.backElevated),
        )
    }
}