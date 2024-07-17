package by.g_alex.ysmd_todo_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import by.g_alex.ysmd_todo_compose.common.workers.DataUpdateWorker
import by.g_alex.ysmd_todo_compose.presentation.NavigationScreen
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.ToDoTheme
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.YSMDToDoComposeTheme
import by.g_alex.ysmd_todo_compose.domain.use_case.theme_setting.ThemeEnum
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val periodicWorkRequest = PeriodicWorkRequestBuilder<DataUpdateWorker>(8, TimeUnit.HOURS)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            /* uniqueWorkName = */ "Update list",
            /* existingPeriodicWorkPolicy = */ ExistingPeriodicWorkPolicy.KEEP,
            /* periodicWork = */ periodicWorkRequest
        )

        val theme by viewModel.theme

        enableEdgeToEdge()
        setContent {

            val isDark = when(theme) {
                ThemeEnum.Dark -> true
                ThemeEnum.Light -> false
                ThemeEnum.Auto -> isSystemInDarkTheme()
            }

            YSMDToDoComposeTheme(darkTheme = isDark) {
                ToDoTheme(darkTheme = isDark) {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        NavigationScreen()
                    }
                }
            }
        }
    }
}
