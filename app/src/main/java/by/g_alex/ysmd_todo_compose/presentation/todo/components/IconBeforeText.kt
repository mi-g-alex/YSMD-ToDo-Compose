package by.g_alex.ysmd_todo_compose.presentation.todo.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme

@Composable
fun IconBeforeText(priority: ToDoPriority, modifier: Modifier = Modifier) {
    if (priority == ToDoPriority.HIGH) {
        Icon(
            painter = painterResource(R.drawable.icon_todo_important),
            contentDescription = stringResource(R.string.todo_priority_high_icon_desc),
            tint = ToDoTheme.colors.colorRed,
            modifier = modifier
        )
    }

    if (priority == ToDoPriority.LOW) {
        Icon(
            painter = painterResource(R.drawable.icon_todo_low),
            contentDescription = stringResource(R.string.todo_priority_low_icon_desc),
            tint = ToDoTheme.colors.colorGray,
            modifier = modifier
        )
    }
}
