package by.g_alex.ysmd_todo_compose.presentation.todo.todo_list

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.g_alex.ysmd_todo_compose.data.listHardCoded
import by.g_alex.ysmd_todo_compose.data.model.ToDoItemModel
import by.g_alex.ysmd_todo_compose.presentation.todo.todo_list.additions.ToDoItem
import by.g_alex.ysmd_todo_compose.presentation.todo.todo_list.additions.ToDoListScreenFAB
import by.g_alex.ysmd_todo_compose.presentation.todo.todo_list.additions.ToDoListScreenTopBar
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme

@Composable
fun ToDoListScreen(
    navToEditAdd: (id: String?) -> Unit,
    viewModel: ToDoListViewModel = hiltViewModel()
) {

    LaunchedEffect(Unit) {
        viewModel.getAllToDos()
    }

    val state = viewModel.state.collectAsState()

    val list = state.value.listOfToDo
    val cnt = state.value.cnt

    ToDoScreenContext(
        list = list,
        isLoading = state.value.isLoading,
        error = state.value.isError,
        cnt = cnt,
        navToEditAdd = navToEditAdd,
        onCompleteClicked = { c, i -> viewModel.completeToDo(c, i) }
    ) { item ->
        viewModel.deleteToDo(item)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoScreenContext(
    list: List<ToDoItemModel>,
    isLoading: Boolean,
    error: String?,
    cnt: Int,
    navToEditAdd: (id: String?) -> Unit,
    onCompleteClicked: (completed: Boolean, item: ToDoItemModel) -> Unit,
    onDeleteClicked: (item: ToDoItemModel) -> Unit
) {

    var showAll by rememberSaveable { mutableStateOf(true) }

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ToDoListScreenTopBar(scrollBehavior, showAll, { showAll = !showAll }, cnt)
        },
        floatingActionButton = {
            ToDoListScreenFAB { navToEditAdd(null) }
        }
    ) { pad ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .background(ToDoTheme.colors.backPrimary),
        ) {

            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            if (!isLoading && error.isNullOrBlank())
                LazyColumn(
                    modifier = Modifier
                        .padding(ToDoTheme.dp.listPadding)
                        .border(
                            width = 8.dp,
                            color = ToDoTheme.colors.backSecondary,
                            shape = RoundedCornerShape(ToDoTheme.dp.shapeCorner)
                        )
                        .padding(ToDoTheme.dp.listContentPadding)
                ) {
                    list.filter { showAll || !it.completed }.forEach { item ->
                        item {
                            ToDoItem(
                                item,
                                onComplete = { completed ->
                                    onCompleteClicked(completed, item)
                                },
                                onCardClick = { navToEditAdd(item.id) },
                                onDelete = { onDeleteClicked(item) }
                            )
                        }

                    }

                    item {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { navToEditAdd(null) },
                            shape = RectangleShape,
                            colors = CardDefaults.cardColors()
                                .copy(containerColor = ToDoTheme.colors.backSecondary)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(ToDoTheme.dp.listContentPadding),
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
                }

            if (!error.isNullOrBlank()) {
                Text(error.toString())
            }
        }
    }
}


// Light screen preview
@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "List on light theme")
private fun ListPreview() {
    ToDoTheme {
        ToDoScreenContext(listHardCoded, false, null, 5, {}, { _, _ -> }, {})
    }
}


// Dark screen preview
@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "List on dark theme")
private fun ListPreviewDark() {
    ToDoTheme {
        ToDoScreenContext(listHardCoded, false, null, 5, {}, { _, _ -> }, {})
    }
}