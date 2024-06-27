package by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo.additions

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.CustomTheme

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
            .padding(vertical = 8.dp),
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = CustomTheme.colors.backSecondary,
            unfocusedContainerColor = CustomTheme.colors.backSecondary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            focusedTextColor = CustomTheme.colors.labelPrimary,
            unfocusedTextColor = CustomTheme.colors.labelPrimary,
            focusedLabelColor = CustomTheme.colors.labelSecondary,
            unfocusedLabelColor = CustomTheme.colors.labelSecondary,
            cursorColor = CustomTheme.colors.labelSecondary
        ),
        label = {
            Text(stringResource(R.string.todo_edit_label))
        },
        shape = RoundedCornerShape(8.dp),
    )
}


@Composable
@Preview(name = "EditTextField | Light | Without text")
fun CreateEditTextFieldPreviewLight() {
    CustomTheme {
        CreateEditTextField("") {}
    }
}

@Composable
@Preview(name = "Edit Top Bar | Dark | Without text", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun CreateEditTextFieldPreviewDark() {
    CustomTheme {
        CreateEditTextField("") {}
    }
}

@Composable
@Preview(name = "Edit Top Bar | Light | Text")
fun CreateEditTextFieldPreviewLightText() {
    CustomTheme {
        CreateEditTextField("Text") {}
    }
}

@Composable
@Preview(name = "Edit Top Bar | Dark | Text", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun CreateEditTextFieldPreviewDarkText() {
    CustomTheme {
        CreateEditTextField("Text") {}
    }
}