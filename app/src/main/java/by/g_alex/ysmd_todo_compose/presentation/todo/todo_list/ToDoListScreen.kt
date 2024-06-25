package by.g_alex.ysmd_todo_compose.presentation.todo.todo_list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.data.repository.ToDoRepository
import by.g_alex.ysmd_todo_compose.presentation.todo.components.ToDoItem
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.CustomTheme
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListScreen(
    rep: ToDoRepository,
    navToEditAdd: (id: String?) -> Unit
) {

    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    var list by remember { mutableStateOf(rep.getAllToDo()) }

    var showAll by rememberSaveable { mutableStateOf(true) }

    var cnt by remember { mutableIntStateOf(rep.countOfCompleted()) }

    LaunchedEffect(cnt) {}

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = CustomTheme.colors.backPrimary,
                    titleContentColor = CustomTheme.colors.labelPrimary,
                    scrolledContainerColor = CustomTheme.colors.backPrimary,
                    actionIconContentColor = CustomTheme.colors.colorBlue
                ),
                title = {
                    Column {
                        Text(
                            text = stringResource(R.string.app_name),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                        )
                        AnimatedVisibility(scrollBehavior.state.heightOffset == 0f) {
                            Text(
                                text = stringResource(R.string.todo_completed, cnt),
                                color = CustomTheme.colors.labelSecondary,
                                fontSize = 20.sp
                            )
                        }
                    }
                },
                actions = {
                    IconButton({ showAll = !showAll }) {
                        Icon(
                            painterResource(if (showAll) R.drawable.view_show else R.drawable.view_hide),
                            null
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                {
                    navToEditAdd(null)
                },
                shape = CircleShape,
                containerColor = CustomTheme.colors.colorBlue,
                contentColor = White
            ) {
                Icon(
                    Icons.Outlined.Add,
                    null
                )
            }
        }
    ) { pad ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(pad)
                .background(CustomTheme.colors.backPrimary)
        ) {
            LazyColumn(
                modifier = Modifier
                    .padding(8.dp)
                    .border(
                        width = 8.dp,
                        color = CustomTheme.colors.backSecondary,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(5.dp)
            ) {
                list.filter { showAll || !it.completed }.forEach { item ->
                    item {
                        ToDoItem(
                            item,
                            onComplete = { completed ->
                                if (completed) cnt++ else cnt--
                                rep.addOrEditToDo(item.copy(completed = completed))
                                list = rep.getAllToDo()
                            },
                            onCardClick = { navToEditAdd(item.id) },
                            onDelete = { rep.deleteToDo(item = item); list = rep.getAllToDo() }
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
                            .copy(containerColor = CustomTheme.colors.backSecondary)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp),
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text(
                                "Create new",
                                color = CustomTheme.colors.labelTertiary,
                                modifier = Modifier.padding(start = 45.dp),
                                fontSize = 20.sp
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
@Preview
fun ListPreview() {
    CustomTheme {
        ToDoListScreen(ToDoRepository(), {})
    }
}