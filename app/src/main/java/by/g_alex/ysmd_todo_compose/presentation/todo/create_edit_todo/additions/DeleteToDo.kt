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
import androidx.compose.ui.unit.dp
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.CustomTheme

@Composable
fun DeleteToDo(
    enabled: Boolean,
    onDelete: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(CustomTheme.colors.backPrimary)
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.End
    ) {
        TextButton(
            onClick = { onDelete() },
            enabled = enabled,
            colors = ButtonDefaults.textButtonColors().copy(
                contentColor = CustomTheme.colors.colorRed,
                disabledContentColor = CustomTheme.colors.labelDisable
            )
        ) {
            Icon(Icons.Filled.Delete, null)
            Text(text = stringResource(R.string.todo_delete))
        }
    }
}


@Composable
@Preview(name = "Delete | Light | Can't delete")
fun DeleteToDoPreviewLight() {
    CustomTheme {
        DeleteToDo(false) {}
    }
}

@Composable
@Preview(name = "Delete | Dark | Can't delete", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun DeleteToDoPreviewDark() {
    CustomTheme {
        DeleteToDo(false) {}
    }
}

@Composable
@Preview(name = "Delete | Light | Can delete")
fun DeleteToDoPreviewLightCanSave() {
    CustomTheme {
        DeleteToDo(true) {}
    }
}

@Composable
@Preview(name = "Delete | Dark | Can delete", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun DeleteToDoPreviewDarkCanSave() {
    CustomTheme {
        DeleteToDo(true) {}
    }
}