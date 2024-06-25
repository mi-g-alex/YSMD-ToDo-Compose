package by.g_alex.ysmd_todo_compose.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = BackDarkPrimary,
    secondary = BackDarkSecondary,
    tertiary = BackDarkElevated,
    onPrimary = White,
    onSecondary = White99,
    onTertiary = White66,
)

private val LightColorScheme = lightColorScheme(
    primary = BackLightPrimary,
    secondary = White,
    tertiary = White,
    onPrimary = Black,
    onSecondary = Black99,
    onTertiary = Black4D,
    background = White
)

val LocalCustomColors = staticCompositionLocalOf {
    CustomColors(
        supportSeparator = Color.Unspecified,
        supportOverlay = Color.Unspecified,
        labelPrimary = Color.Unspecified,
        labelSecondary = Color.Unspecified,
        labelTertiary = Color.Unspecified,
        labelDisable = Color.Unspecified,
        colorRed = Color.Unspecified,
        colorGreen = Color.Unspecified,
        colorBlue = Color.Unspecified,
        colorGray = Color.Unspecified,
        colorGrayLight = Color.Unspecified,
        backPrimary = Color.Unspecified,
        backSecondary = Color.Unspecified,
        backElevated = Color.Unspecified,
    )
}

@Composable
fun YSMDToDoComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

val lightColors = CustomColors(
    supportSeparator = Black33,
    supportOverlay = Black0F,
    labelPrimary = Black,
    labelSecondary = Black99,
    labelTertiary = Black4D,
    labelDisable = Black26,
    colorRed = RedLight,
    colorGreen = GreenLight,
    colorBlue = BlueLight,
    colorGray = Gray,
    colorGrayLight = GrayLight,
    backPrimary = BackLightPrimary,
    backSecondary = White,
    backElevated = White
)

val darkColors = CustomColors(
    supportSeparator = White33,
    supportOverlay = Black52,
    labelPrimary = White,
    labelSecondary = White99,
    labelTertiary = White66,
    labelDisable = White26,
    colorRed = RedDark,
    colorGreen = GreenDark,
    colorBlue = BlueDark,
    colorGray = Gray,
    colorGrayLight = GrayDark,
    backPrimary = BackDarkPrimary,
    backSecondary = BackDarkSecondary,
    backElevated = BackDarkElevated
)

@Composable
fun CustomTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = when {
        darkTheme -> darkColors
        else -> lightColors
    }

    CompositionLocalProvider(
        LocalCustomColors provides colors,
        content = content
    )
}

object CustomTheme {
    val colors: CustomColors
        @Composable
        get() = LocalCustomColors.current
}
