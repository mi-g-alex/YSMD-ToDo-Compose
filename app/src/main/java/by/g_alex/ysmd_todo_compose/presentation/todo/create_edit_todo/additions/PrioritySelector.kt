package by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo.additions

import android.content.res.Configuration
import android.graphics.Color
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import by.g_alex.ysmd_todo_compose.presentation.todo.components.bounceClick
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.Black
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.YSMDToDoComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
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
            .bounceClick { expanded = true }
            .padding(vertical = ToDoTheme.dp.listVerticalPadding)
    ) {
        Text(
            text = "Priority",
            style = ToDoTheme.typography.body,
            color = ToDoTheme.colors.labelPrimary,
        )

        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {

            IconBeforeText(
                priority = priority,
                modifier = Modifier.size(ToDoTheme.typography.support.fontSize.value.dp)
            )

            Text(
                text = priorityText,
                color = ToDoTheme.colors.labelSecondary,
                fontSize = 14.sp
            )
        }

//        PriorityDropDownMenu(expanded, { expanded = false }) {
//            selectPriority(it); expanded = false
//        }
    }

    val sheetState = rememberModalBottomSheetState()

    if(expanded) {
        PriorityBottomSheet(
            onDismiss = {
                expanded = false
            },
            sheetState = sheetState
        ) { item ->
            selectPriority(item)
        }
    }

    HorizontalDivider(modifier = Modifier.fillMaxWidth())
}

@Composable
@Preview(name = "Priority Selector | Light")
private fun PrioritySelectorPreviewLight() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            PrioritySelector(ToDoPriority.HIGH) {}
        }
    }
}

@Composable
@Preview(
    name = "Priority Selector | Dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    backgroundColor = Color.BLACK.toLong()
)
private fun PrioritySelectorPreviewDark() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            PrioritySelector(ToDoPriority.HIGH) {}
        }
    }
}
