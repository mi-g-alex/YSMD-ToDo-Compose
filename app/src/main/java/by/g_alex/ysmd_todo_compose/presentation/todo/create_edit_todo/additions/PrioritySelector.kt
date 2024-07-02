package by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo.additions

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority
import by.g_alex.ysmd_todo_compose.presentation.todo.components.IconBeforeText
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme

@Composable
fun PrioritySelector(
    priority: ToDoPriority,
    selectPriority: (selected: ToDoPriority) -> Unit,
) {

    val priorityText = when (priority) {
        ToDoPriority.NONE -> stringResource(R.string.todo_priority_none)
        ToDoPriority.HIGH -> stringResource(R.string.todo_priority_high)
        ToDoPriority.LOW -> stringResource(R.string.todo_priority_low)
    }

    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(ToDoTheme.colors.backPrimary)
            .padding(vertical = ToDoTheme.dp.listVerticalPadding)
            .clickable { expanded = true }
    ) {
        Text(
            text = "Priority",
            style = ToDoTheme.typography.body,
            color = ToDoTheme.colors.labelPrimary,
        )

        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

            IconBeforeText(
                priority = priority,
                // TODO: Here need fix static dp bcs it should be like font
                modifier = Modifier.size(ToDoTheme.typography.support.fontSize.value.dp)
            )

            Text(
                text = priorityText,
                color = ToDoTheme.colors.labelSecondary,
                fontSize = 14.sp
            )
        }

        PriorityDropDownMenu(expanded, { expanded = false }) {
            selectPriority(it); expanded = false
        }
    }

    HorizontalDivider(modifier = Modifier.fillMaxWidth())
}

@Composable
@Preview(name = "Priority Selector | Light")
private fun PrioritySelectorPreviewLight() {
    ToDoTheme {
        PrioritySelector(ToDoPriority.HIGH) {}
    }
}

@Composable
@Preview(name = "Priority Selector | Dark", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun PrioritySelectorPreviewDark() {
    ToDoTheme {
        PrioritySelector(ToDoPriority.HIGH) {}
    }
}
