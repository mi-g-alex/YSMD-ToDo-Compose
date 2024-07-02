package by.g_alex.ysmd_todo_compose.presentation.todo.todo_list.additions

import android.content.res.Configuration
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.White

@Composable
fun ToDoListScreenFAB(
    navToEditAdd: () -> Unit
) {
    FloatingActionButton(
        onClick = { navToEditAdd() },
        shape = CircleShape,
        containerColor = ToDoTheme.colors.colorBlue,
        contentColor = White
    ) {
        Icon(
            Icons.Outlined.Add,
            null
        )
    }
}

@Composable
@Preview(name = "FAB", showBackground = false)
private fun ToDoListFABPreview() {
    ToDoTheme {
        ToDoListScreenFAB { }
    }
}

@Composable
@Preview(name = "FAB", showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ToDoListFABPreviewDark() {
    ToDoTheme {
        ToDoListScreenFAB { }
    }
}