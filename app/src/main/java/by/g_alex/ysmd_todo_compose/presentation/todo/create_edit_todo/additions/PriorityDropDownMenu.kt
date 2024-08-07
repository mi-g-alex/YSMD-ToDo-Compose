package by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo.additions

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority
import by.g_alex.ysmd_todo_compose.presentation.todo.components.IconBeforeText
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.YSMDToDoComposeTheme

@Composable
fun PriorityDropDownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onSelect: (selected: ToDoPriority) -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismiss() },
        modifier = Modifier.background(ToDoTheme.colors.backElevated)
    ) {
        DropdownMenuItem(
            text = {
                Text(
                    stringResource(R.string.todo_priority_none),
                    style = ToDoTheme.typography.support
                )
            },
            onClick = { onSelect(ToDoPriority.NONE) },
            modifier = Modifier.background(ToDoTheme.colors.backElevated),
        )
        DropdownMenuItem(
            text = {
                Text(
                    stringResource(R.string.todo_priority_high),
                    color = ToDoTheme.colors.colorRed,
                    style = ToDoTheme.typography.support
                )
            },
            onClick = { onSelect(ToDoPriority.HIGH) },
            modifier = Modifier.background(ToDoTheme.colors.backElevated),
            leadingIcon = { IconBeforeText(ToDoPriority.HIGH) }
        )
        DropdownMenuItem(
            text = {
                Text(
                    stringResource(R.string.todo_priority_low),
                    style = ToDoTheme.typography.support
                )
            },
            onClick = { onSelect(ToDoPriority.LOW) },
            modifier = Modifier.background(ToDoTheme.colors.backElevated),
            leadingIcon = { IconBeforeText(ToDoPriority.LOW) },
        )
    }
}

@Composable
@Preview(name = "Priority DropDown Menu | Light")
private fun PriorityDropDownMenuPreviewLight() {
    // Work only on Interactive mode of preview
    YSMDToDoComposeTheme {
        ToDoTheme {
            PriorityDropDownMenu(true, {}) {}
        }
    }
}

@Composable
@Preview(name = "Priority DropDown Menu | Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PriorityDropDownMenuPreviewDark() {
    // Work only on Interactive mode of preview
    YSMDToDoComposeTheme {
        ToDoTheme {
            PriorityDropDownMenu(true, {}) {}
        }
    }
}