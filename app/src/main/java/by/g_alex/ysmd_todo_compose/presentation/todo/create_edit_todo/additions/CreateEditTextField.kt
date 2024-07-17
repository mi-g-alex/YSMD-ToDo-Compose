package by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo.additions

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.YSMDToDoComposeTheme

@Composable
fun CreateEditTextField(
    text: String,
    onEditText: (text: String) -> Unit
) {
    TextField(
        value = text,
        onValueChange = { onEditText(it) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = ToDoTheme.dp.listVerticalPadding),
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = ToDoTheme.colors.backSecondary,
            unfocusedContainerColor = ToDoTheme.colors.backSecondary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = ToDoTheme.colors.labelSecondary,
            focusedLabelColor = ToDoTheme.colors.colorBlue,
            unfocusedLabelColor = ToDoTheme.colors.colorBlue,
            textSelectionColors = TextSelectionColors(
                ToDoTheme.colors.colorBlue,
                ToDoTheme.colors.colorBlue.copy(alpha = 0.5f)
            ),
        ),
        label = {
            Text(
                stringResource(R.string.todo_edit_label),
                style = ToDoTheme.typography.label
            )
        },
        textStyle = ToDoTheme.typography.body,
        shape = RoundedCornerShape(ToDoTheme.dp.shapeCorner),
    )
}


@Composable
@Preview(name = "EditTextField | Light | Without text")
private fun CreateEditTextFieldPreviewLight() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            CreateEditTextField("") {}
        }
    }
}

@Composable
@Preview(name = "Edit Top Bar | Dark | Without text", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun CreateEditTextFieldPreviewDark() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            CreateEditTextField("") {}
        }
    }
}

@Composable
@Preview(name = "Edit Top Bar | Light | Text")
private fun CreateEditTextFieldPreviewLightText() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            CreateEditTextField("Text") {}
        }
    }
}

@Composable
@Preview(name = "Edit Top Bar | Dark | Text", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun CreateEditTextFieldPreviewDarkText() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            CreateEditTextField("Text") {}
        }
    }
}