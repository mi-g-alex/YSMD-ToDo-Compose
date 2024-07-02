package by.g_alex.ysmd_todo_compose.presentation.todo.todo_list.additions

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.CustomTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListScreenTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    showAll: Boolean,
    onShowClicked: () -> Unit,
    cnt: Int
) {
    // Тут что-то типо анимации для elevation
    Surface(shadowElevation = (scrollBehavior.state.collapsedFraction * 40).dp) {
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
                IconButton({ onShowClicked() }) {
                    Icon(
                        painterResource(if (showAll) R.drawable.view_show else R.drawable.view_hide),
                        null
                    )
                }
            },
            scrollBehavior = scrollBehavior
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(name = "ToDoList Top Bar")
fun ToDoTopBarPreview() {
    CustomTheme {
        val t = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
        ToDoListScreenTopBar(t, false, {}, 0)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(name = "ToDoList Top Bar", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ToDoTopBarPreviewDark() {
    CustomTheme {
        val t = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
        ToDoListScreenTopBar(t, true, {}, 0)
    }
}