package by.g_alex.ysmd_todo_compose.presentation.todo.todo_list.additions

import android.content.res.Configuration
import android.util.Log
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.White
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.YSMDToDoComposeTheme

@Composable
fun ToDoListScreenFAB(
    navToEditAdd: () -> Unit
) {
    val desc = stringResource(R.string.todo_create)
    FloatingActionButton(
        modifier = Modifier.semantics(mergeDescendants = true) {
            contentDescription = desc
        },
        onClick = { navToEditAdd() },
        shape = CircleShape,
    ) {
        Icon(
            imageVector = Icons.Outlined.Add,
            contentDescription = desc
        )
    }
}

@Composable
@Preview(name = "FAB", showBackground = false)
private fun ToDoListFABPreview() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            ToDoListScreenFAB { }
        }
    }
}

@Composable
@Preview(name = "FAB", showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ToDoListFABPreviewDark() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            ToDoListScreenFAB { }
        }
    }
}