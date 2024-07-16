package by.g_alex.ysmd_todo_compose.presentation.todo.todo_list

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.data.listHardCoded
import by.g_alex.ysmd_todo_compose.domain.model.ToDoItemModel
import by.g_alex.ysmd_todo_compose.presentation.todo.components.AuthDialog
import by.g_alex.ysmd_todo_compose.presentation.todo.components.ErrorDialog
import by.g_alex.ysmd_todo_compose.presentation.todo.todo_list.additions.AddNewItemCard
import by.g_alex.ysmd_todo_compose.presentation.todo.todo_list.additions.ToDoItem
import by.g_alex.ysmd_todo_compose.presentation.todo.todo_list.additions.ToDoListScreenFAB
import by.g_alex.ysmd_todo_compose.presentation.todo.todo_list.additions.ToDoListScreenTopBar
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.YSMDToDoComposeTheme

@Composable
fun ToDoListScreen(
    navToEditAdd: (id: String?) -> Unit,
    viewModel: ToDoListViewModel
) {

    val state = viewModel.state.collectAsState()

    val list = state.value.listOfToDo
    val cnt = state.value.cnt


    ToDoScreenContext(
        list = list,
        isLoading = state.value.isLoading,
        error = state.value.isError,
        cnt = cnt,
        navToEditAdd = navToEditAdd,
        onCompleteClicked = { c, i -> viewModel.completeToDo(c, i) },
        onDeleteClicked = { item -> viewModel.deleteToDo(item) },
        authToken = state.value.authToken,
        authIsBearer = state.value.isBearerToken,
        saveToken = { t, b -> viewModel.saveAuthToken(t, b) },
        refreshPage = { viewModel.getAllToDos() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoScreenContext(
    list: List<ToDoItemModel>,
    isLoading: Boolean,
    @StringRes error: Int?,
    cnt: Int,
    navToEditAdd: (id: String?) -> Unit,
    onCompleteClicked: (completed: Boolean, item: ToDoItemModel) -> Unit,
    onDeleteClicked: (item: ToDoItemModel) -> Unit,
    authToken: String,
    authIsBearer: Boolean?,
    saveToken: (token: String?, isBearer: Boolean?) -> Unit,
    refreshPage: () -> Unit
) {

    var showAll by remember { mutableStateOf(true) }

    var showAuthDialog by remember { mutableStateOf(false) }

    val state = rememberPullToRefreshState()

    if (state.isRefreshing) {
        LaunchedEffect(true) {
            refreshPage()
            if (!isLoading)
                state.endRefresh()
        }
        LaunchedEffect(isLoading) {
            if (!isLoading)
                state.endRefresh()
        }
    }

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = Modifier
            .nestedScroll(state.nestedScrollConnection)
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            ToDoListScreenTopBar(
                scrollBehavior,
                showAll,
                { showAll = !showAll },
                { showAuthDialog = true },
                cnt
            )
        },
        floatingActionButton = {
            ToDoListScreenFAB { navToEditAdd(null) }
        }
    ) { pad ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad),
        ) {

            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.align(Alignment.TopCenter))
            }

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
                    AddNewItemCard { navToEditAdd(null) }
                }
            }



            if (error != null)
                Text(
                    stringResource(error),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)
                        .background(ToDoTheme.colors.colorRed),
                    color = Color.White,
                    style = ToDoTheme.typography.support,
                    textAlign = TextAlign.Center
                )


            PullToRefreshContainer(
                state = state,
                modifier = Modifier.align(Alignment.TopCenter),
                containerColor = ToDoTheme.colors.backElevated,
                contentColor = ToDoTheme.colors.labelTertiary
            )

        }
    }

    if (showAuthDialog)
        AuthDialog(
            authToken = authToken,
            authIsBearer = authIsBearer,
            onSaveClick = { t, b -> saveToken(t, b) }) { showAuthDialog = false }
}


// Light screen preview
@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "List on light theme")
private fun ListPreview() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            ToDoScreenContext(
                emptyList(),
                isLoading = true,
                error = null,
                cnt = 5,
                navToEditAdd = {},
                onCompleteClicked = { _, _ -> },
                onDeleteClicked = {},
                authToken = "",
                authIsBearer = true,
                saveToken = { _, _ -> },
                refreshPage = {}
            )
        }
    }
}


// Dark screen preview
@Composable
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, name = "List on dark theme")
private fun ListPreviewDark() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            ToDoScreenContext(
                listHardCoded,
                isLoading = false,
                error = null,
                cnt = 5,
                navToEditAdd = {},
                onCompleteClicked = { _, _ -> },
                onDeleteClicked = {},
                authToken = "",
                authIsBearer = true,
                saveToken = { _, _ -> },
                refreshPage = {})
        }
    }
}