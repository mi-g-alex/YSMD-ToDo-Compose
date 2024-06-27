package by.g_alex.ysmd_todo_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import by.g_alex.ysmd_todo_compose.presentation.NavigationScreen
import by.g_alex.ysmd_todo_compose.presentation.ui.theme.CustomTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CustomTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavigationScreen()
                }
            }
        }
    }

}
