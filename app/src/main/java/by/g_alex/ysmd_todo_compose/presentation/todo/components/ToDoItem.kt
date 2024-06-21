package by.g_alex.ysmd_todo_compose.presentation.todo.components

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority
import by.g_alex.ysmd_todo_compose.data.model.ToDoItemModel
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.CustomColors
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.CustomTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ToDoItem(
    item: ToDoItemModel,
    onComplete: (newState: Boolean) -> Unit,
    onCardClick: () -> Unit,
    onDelete: () -> Unit,
) {

    var showMenu by remember { mutableStateOf(false) }

    val checkBoxColor = if (item.priority == ToDoPriority.HIGH) {
        CheckboxDefaults.colors().copy(
            checkedBorderColor = CustomTheme.colors.colorGreen,
            checkedBoxColor = CustomTheme.colors.colorGreen,
            uncheckedBoxColor = CustomTheme.colors.colorRed.copy(alpha = 0.16f),
            uncheckedBorderColor = CustomTheme.colors.colorRed
        )
    } else {
        CheckboxDefaults.colors().copy(
            checkedBorderColor = CustomTheme.colors.colorGreen,
            checkedBoxColor = CustomTheme.colors.colorGreen
        )
    }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onCardClick() },
                onLongClick = { showMenu = true }
            ),
        shape = RectangleShape,
        colors = CardDefaults.cardColors()
            .copy(containerColor = CustomTheme.colors.backSecondary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = item.completed,
                onCheckedChange = {
                    onComplete(it)
                },
                colors = checkBoxColor
            )

            IconBeforeText(priority = item.priority, modifier = Modifier.padding(end = 4.dp))

            Column(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = item.text,
                    fontSize = 16.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    color = CustomTheme.colors.labelPrimary,
                    style = if (item.completed) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle()
                )

                if (item.deadline != null) {
                    Text(
                        text = SimpleDateFormat(
                            "dd.MM.yyyy",
                            Locale.getDefault()
                        ).format(item.deadline),
                        color = CustomTheme.colors.labelTertiary
                    )
                }

            }
            Icon(
                Icons.Outlined.Info,
                null,
                tint = CustomTheme.colors.colorGray,
                modifier = Modifier.padding(start = 4.dp)
            )

        }

        DropDownMenu(
            expanded = showMenu,
            onDismiss = { showMenu = false },
            onEdit = { onCardClick(); showMenu = false }
        ) { onDelete(); showMenu = false }

    }


}

@Composable
private fun DropDownMenu(
    expanded: Boolean,
    onDismiss: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { onDismiss() },
        modifier = Modifier.background(CustomTheme.colors.backElevated)
    ) {
        DropdownMenuItem(
            text = { Text(stringResource(R.string.todo_edit), color = CustomTheme.colors.labelPrimary) },
            onClick = { onEdit() },
            modifier = Modifier.background(CustomTheme.colors.backElevated),
        )
        DropdownMenuItem(
            text = { Text(stringResource(R.string.todo_delete), color = CustomTheme.colors.colorRed) },
            onClick = { onDelete() },
            modifier = Modifier.background(CustomTheme.colors.backElevated),
        )
    }
}

@Preview
@Composable
fun ToDoItemBasic() {
    CustomTheme {
        ToDoItem(
            ToDoItemModel(
                id = "1",
                text = "It is first note",
                priority = ToDoPriority.NONE,
                completed = false,
                deadline = null,
                updateDate = null
            ),
            {},
            {},
            {},
        )
    }
}

@Preview
@Composable
fun ToDoItemHighPriority() {
    CustomTheme {
        ToDoItem(
            ToDoItemModel(
                id = "2",
                text = "It is second note",
                priority = ToDoPriority.HIGH,
                completed = true,
                deadline = Date(1734775200000),
                updateDate = null
            ),
            {},
            {},
            {},
        )
    }
}

@Preview
@Composable
fun ToDoItemWithDeadline() {
    CustomTheme {
        ToDoItem(
            ToDoItemModel(
                id = "1",
                text = "It is first note",
                priority = ToDoPriority.NONE,
                completed = false,
                deadline = null,
                updateDate = null
            ),
            {},
            {},
            {},
        )
    }
}

@Preview
@Composable
fun ToDoItemLowPriority() {
    CustomTheme {
        ToDoItem(
            ToDoItemModel(
                id = "3",
                text = "It is third note",
                priority = ToDoPriority.LOW,
                completed = true,
                deadline = Date(1734774200000),
                updateDate = Date(1734774190000)
            ),
            {},
            {},
            {},
        )
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun ToDoItemLotText() {
    CustomTheme {
        ToDoItem(
            ToDoItemModel(
                id = "4",
                text = "A lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot lot text",
                priority = ToDoPriority.HIGH,
                completed = false,
                deadline = Date(1734734200000),
                updateDate = Date(1734774190000)
            ),
            {},
            {},
            {},
        )
    }
}