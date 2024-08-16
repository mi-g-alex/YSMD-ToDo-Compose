package by.g_alex.ysmd_todo_compose.presentation.todo.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme.colors

@Composable
fun AuthDialog(
    authToken: String,
    authIsBearer: Boolean?,
    onSaveClick: (token: String?, isBearer: Boolean?) -> Unit,
    onDismiss: () -> Unit
) {

    var isBearer by remember { mutableStateOf(authIsBearer ?: true) }
    var token by remember { mutableStateOf(authToken) }

    AlertDialog(
        containerColor = ToDoTheme.colors.backPrimary,
        titleContentColor = colors.labelPrimary,
        textContentColor = colors.labelPrimary,
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(onClick = {
                if (token.isBlank()) {
                    onSaveClick(null, null)
                } else {
                    onSaveClick(token, isBearer)
                }
                onDismiss()
            }) {
                Text(
                    text = stringResource(R.string.auth_save),
                    style = ToDoTheme.typography.button,
                    color = colors.colorBlue,
                )
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text(
                    text = stringResource(R.string.auth_cancel),
                    style = ToDoTheme.typography.button,
                    color = colors.colorBlue,
                )
            }
        },
        text = {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics(mergeDescendants = true) { }
                        .bounceClick { isBearer = true },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = isBearer,
                        onClick = { isBearer = true },
                        colors = RadioButtonDefaults.colors().copy(
                            selectedColor = colors.colorBlue,
                            unselectedColor = colors.colorGray
                        )
                    )
                    Text(
                        stringResource(R.string.rb_bearer_token),
                        style = ToDoTheme.typography.body,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics(mergeDescendants = true) { }
                        .bounceClick { isBearer = false },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = !isBearer,
                        onClick = { isBearer = false },
                        colors = RadioButtonDefaults.colors().copy(
                            selectedColor = colors.colorBlue,
                            unselectedColor = colors.colorGray
                        )
                    )
                    Text(
                        stringResource(R.string.rb_auth_2_token),
                        style = ToDoTheme.typography.body
                    )
                }
                TextField(
                    maxLines = 1,
                    label = {
                        Text(stringResource(R.string.auth_token_label))
                    },
                    value = token,
                    onValueChange = {
                        if (
                            it.isEmpty() ||
                            it.last() in 'a'..'z' || it.last() in 'A'..'Z' || it.last() in '0'..'9' || it.last() == '_'
                        ) {
                            token = it
                        }
                    },
                    colors = TextFieldDefaults.colors().copy(
                        focusedContainerColor = colors.backSecondary,
                        unfocusedContainerColor = colors.backSecondary,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = colors.labelPrimary,
                        unfocusedTextColor = colors.labelPrimary,
                        focusedLabelColor = colors.labelSecondary,
                        unfocusedLabelColor = colors.labelSecondary,
                        cursorColor = colors.labelSecondary
                    ),
                    shape = RoundedCornerShape(ToDoTheme.dp.shapeCorner),
                )
            }
        }
    )
}


@Composable
@Preview
private fun DialogPreview() {
    ToDoTheme {
        AuthDialog("123", true, { _, _ -> }) { }
    }
}