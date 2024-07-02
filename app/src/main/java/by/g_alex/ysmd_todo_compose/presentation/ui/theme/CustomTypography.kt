package by.g_alex.ysmd_todo_compose.presentation.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Immutable
data class CustomTypography(
    val subTitle: TextStyle,
    val body: TextStyle,
    val support: TextStyle,
    val button: TextStyle,
    val topBarButton: TextStyle,
    val label: TextStyle
)

internal val LocalCustomTypography = staticCompositionLocalOf {
    CustomTypography(
        subTitle = TextStyle(
            fontSize = 20.sp,
            fontWeight = FontWeight.Normal,
        ),
        body = TextStyle(
            fontSize = 16.sp,
        ),
        support = TextStyle(
            fontSize = 14.sp
        ),
        button = TextStyle(),
        topBarButton = TextStyle(
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        ),
        label = TextStyle(
            fontSize = 14.sp,
        ),
    )
}
