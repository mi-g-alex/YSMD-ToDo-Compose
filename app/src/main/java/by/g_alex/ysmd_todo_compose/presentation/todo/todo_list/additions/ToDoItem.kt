package by.g_alex.ysmd_todo_compose.presentation.todo.todo_list.additions

import android.content.res.Configuration
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority
import by.g_alex.ysmd_todo_compose.domain.model.ToDoItemModel
import by.g_alex.ysmd_todo_compose.presentation.todo.components.IconBeforeText
import by.g_alex.ysmd_todo_compose.presentation.todo.components.bounceClick
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme
import by.g_alex.ysmd_todo_compose.presentation.utils.dateToString
import java.util.Date

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
            checkedBorderColor = ToDoTheme.colors.colorGreen,
            checkedBoxColor = ToDoTheme.colors.colorGreen,
            uncheckedBoxColor = ToDoTheme.colors.colorRed.copy(alpha = 0.16f),
            uncheckedBorderColor = ToDoTheme.colors.colorRed
        )
    } else {
        CheckboxDefaults.colors().copy(
            checkedBorderColor = ToDoTheme.colors.colorGreen,
            checkedBoxColor = ToDoTheme.colors.colorGreen,
            uncheckedBoxColor = ToDoTheme.colors.colorGray.copy(alpha = 0.16f),
            uncheckedBorderColor = ToDoTheme.colors.colorGray
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
            .copy(containerColor = ToDoTheme.colors.backSecondary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ToDoTheme.dp.listContentPadding),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(
                checked = item.completed,
                onCheckedChange = {
                    onComplete(it)
                },
                modifier = Modifier.bounceClick(0.6f),
                colors = checkBoxColor
            )

            IconBeforeText(
                priority = item.priority,
                modifier = Modifier.padding(end = ToDoTheme.dp.iconPadding)
            )

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
                    color = ToDoTheme.colors.labelPrimary,
                    style = ToDoTheme.typography.body.copy(textDecoration = if (item.completed) TextDecoration.LineThrough else null)
                )

                if (item.deadline != null) {
                    Text(
                        text = dateToString(item.deadline),
                        color = ToDoTheme.colors.labelTertiary,
                        style = ToDoTheme.typography.support
                    )
                }

            }

            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = stringResource(R.string.todo_edit),
                tint = ToDoTheme.colors.colorGray,
                modifier = Modifier.padding(start = ToDoTheme.dp.iconPadding)
            )

        }

        DropDownMenu(
            expanded = showMenu,
            onDismiss = { showMenu = false },
            onEdit = { onCardClick(); showMenu = false }
        ) { onDelete(); showMenu = false }

    }
}

@Preview
@Composable
private fun ToDoItemBasic() {
    ToDoTheme {
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
private fun ToDoItemHighPriority() {
    ToDoTheme {
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
private fun ToDoItemWithDeadline() {
    ToDoTheme {
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
private fun ToDoItemLowPriority() {
    ToDoTheme {
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
private fun ToDoItemLotText() {
    ToDoTheme {
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