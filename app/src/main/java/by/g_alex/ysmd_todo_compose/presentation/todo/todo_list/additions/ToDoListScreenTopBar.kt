package by.g_alex.ysmd_todo_compose.presentation.todo.todo_list.additions

import android.content.Intent
import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Info
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import by.g_alex.ysmd_todo_compose.R
import by.g_alex.ysmd_todo_compose.divkit.AboutActivity
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.YSMDToDoComposeTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoListScreenTopBar(
    scrollBehavior: TopAppBarScrollBehavior,
    showAll: Boolean,
    onShowClicked: () -> Unit,
    onAuthClick: () -> Unit,
    onSettingsClick: () -> Unit,
    cnt: Int
) {

    val ctx = LocalContext.current

    // Something like animation for elevation
    Surface(shadowElevation = (scrollBehavior.state.collapsedFraction * 40).dp) {
        LargeTopAppBar(
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
                            color = ToDoTheme.colors.labelSecondary,
                            style = ToDoTheme.typography.subTitle
                        )
                    }
                }
            },
            actions = {
                IconButton({ onShowClicked() }) {
                    Icon(
                        painter = painterResource(if (showAll) R.drawable.view_show else R.drawable.view_hide),
                        contentDescription = stringResource(if (showAll) R.string.todo_hide_completed else R.string.todo_show_all)
                    )
                }
                IconButton({ onAuthClick() }) {
                    Icon(
                        painter = painterResource(R.drawable.icon_auth),
                        contentDescription = stringResource(R.string.auth_token_label)
                    )
                }
                IconButton({ onSettingsClick() }) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = stringResource(R.string.settings_title)
                    )
                }
                IconButton(
                    onClick = {
                        val a = Intent(ctx, AboutActivity::class.java)
                        ctx.startActivity(a)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Info,
                        contentDescription = stringResource(R.string.todo_about_app)
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
private fun ToDoTopBarPreview() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            val t = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
            ToDoListScreenTopBar(t, false, {}, {}, {}, 0)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview(name = "ToDoList Top Bar", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ToDoTopBarPreviewDark() {
    YSMDToDoComposeTheme {
        ToDoTheme {
            val t = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
            ToDoListScreenTopBar(t, true, {}, {}, {}, 0)
        }
    }
}