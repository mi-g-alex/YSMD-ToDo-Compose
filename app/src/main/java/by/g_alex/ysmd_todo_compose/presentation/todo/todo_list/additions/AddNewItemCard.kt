package by.g_alex.ysmd_todo_compose.presentation.todo.todo_list.additions

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.sp
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme

@Composable
fun AddNewItemCard(
    navToEditAdd: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { navToEditAdd() }
            .semantics(mergeDescendants = true) {  },
        shape = RectangleShape,
        colors = CardDefaults.cardColors()
            .copy(containerColor = ToDoTheme.colors.backSecondary)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(ToDoTheme.dp.listContentPadding).semantics(mergeDescendants = true) {  },
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                "Create new",
                color = ToDoTheme.colors.labelTertiary,
                modifier = Modifier.padding(start = ToDoTheme.dp.titleStartPadding),
                fontSize = 20.sp
            )
        }
    }
}