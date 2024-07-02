package by.g_alex.ysmd_todo_compose.presentation.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Immutable
data class CustomDp(
    val listHorizontalPadding: Dp,
    val listVerticalPadding: Dp,
    val listPadding: Dp,
    val listContentPadding: Dp,
    val shapeCorner: Dp,
    val titleStartPadding: Dp,
    val iconPadding: Dp,
)

internal val LocalCustomDp = staticCompositionLocalOf {
    CustomDp(
        listHorizontalPadding = 8.dp,
        listVerticalPadding = 8.dp,
        listPadding = 8.dp,
        listContentPadding = 4.dp,
        shapeCorner = 8.dp,
        titleStartPadding = 45.dp,
        iconPadding = 4.dp
    )
}