package by.g_alex.ysmd_todo_compose.presentation.todo.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.CustomTheme

@Composable
fun IconBeforeText(priority: ToDoPriority, modifier: Modifier = Modifier) {
    if (priority == ToDoPriority.HIGH) {
        Icon(
            painterResource(R.drawable.icon_todo_important),
            null,
            tint = CustomTheme.colors.colorRed,
            modifier = modifier
        )
    }

    if (priority == ToDoPriority.LOW) {
        Icon(
            painterResource(R.drawable.icon_todo_low),
            contentDescription = null,
            tint = CustomTheme.colors.colorGray,
            modifier = modifier
        )
    }
}
