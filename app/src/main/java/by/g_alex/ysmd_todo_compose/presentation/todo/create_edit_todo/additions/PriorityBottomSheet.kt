@file:OptIn(ExperimentalMaterial3Api::class)

package by.g_alex.ysmd_todo_compose.presentation.todo.create_edit_todo.additions

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.data.additional.enums.ToDoPriority
import by.g_alex.ysmd_todo_compose.presentation.todo.components.IconBeforeText
import by.g_alex.ysmd_todo_compose.presentation.todo.components.bounceClick
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme
import kotlinx.coroutines.launch

@Composable
fun PriorityBottomSheet(
    onDismiss: () -> Unit,
    sheetState: SheetState,
    onSelect: (selected: ToDoPriority) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = sheetState,
    ) {
        Column {
            PriorityBottomSheetItem(
                ToDoPriority.NONE,
                stringResource(R.string.todo_priority_none),
                sheetState,
                onSelect,
                onDismiss
            )
            PriorityBottomSheetItem(
                ToDoPriority.LOW,
                stringResource(R.string.todo_priority_low),
                sheetState,
                onSelect,
                onDismiss
            )
            PriorityBottomSheetItem(
                ToDoPriority.HIGH,
                stringResource(R.string.todo_priority_high),
                sheetState,
                onSelect,
                onDismiss
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PriorityBottomSheetItem(
    priority: ToDoPriority,
    text: String,
    sheetState: SheetState,
    onSelect: (selected: ToDoPriority) -> Unit,
    onDismiss: () -> Unit,
) {

    val scope = rememberCoroutineScope()

    val color = remember { Animatable(Color.Transparent) }

    Row(
        Modifier
            .fillMaxWidth()
            .background(color = color.value)
            .bounceClick {
                onSelect(priority)
                scope.launch {
                    if (priority == ToDoPriority.HIGH) {
                        color.animateTo(Color.Red.copy(alpha = 0.5f), animationSpec = tween(1000))
                        color.animateTo(Color.Transparent, animationSpec = tween(1000))
                    }
                    sheetState.hide()
                }.invokeOnCompletion {
                    if(!sheetState.isVisible) {
                        onDismiss()
                    }
                }
            }.padding(ToDoTheme.dp.bottomSheetPadding)
    ) {
        IconBeforeText(priority)
        Text(
            text = text,
            style = ToDoTheme.typography.body
        )
    }
}

@Composable
@Preview
private fun PreviewItem() {
    ToDoTheme {
        PriorityBottomSheetItem(
            ToDoPriority.HIGH,
            "High",
            rememberModalBottomSheetState(),
            {}, {}
        )
    }
}