package by.g_alex.ysmd_todo_compose.presentation.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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

// Кастомная тема с поддержкой светлой или тёмной темы
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

@Composable
fun ThemePreviewFun() {
    Column {
        Text(
            "supportSeparator",
            modifier = Modifier
                .fillMaxWidth()
                .background(CustomTheme.colors.supportSeparator)
                .padding(8.dp)
        )
        Text(
            "supportOverlay",
            modifier = Modifier
                .fillMaxWidth()
                .background(CustomTheme.colors.supportOverlay)
                .padding(8.dp)
        )
        Text(
            "labelPrimary",
            modifier = Modifier
                .fillMaxWidth()
                .background(CustomTheme.colors.labelPrimary)
                .padding(8.dp),
            color = CustomTheme.colors.backPrimary
        )
        Text(
            "labelSecondary",
            modifier = Modifier
                .fillMaxWidth()
                .background(CustomTheme.colors.labelSecondary)
                .padding(8.dp)
        )
        Text(
            "labelTertiary",
            modifier = Modifier
                .fillMaxWidth()
                .background(CustomTheme.colors.labelTertiary)
                .padding(8.dp)
        )
        Text(
            "labelDisable",
            modifier = Modifier
                .fillMaxWidth()
                .background(CustomTheme.colors.labelDisable)
                .padding(8.dp)
        )
        Text(
            "colorRed", modifier = Modifier
                .fillMaxWidth()
                .background(CustomTheme.colors.colorRed)
                .padding(8.dp)
        )
        Text(
            "colorGreen",
            modifier = Modifier
                .fillMaxWidth()
                .background(CustomTheme.colors.colorGreen)
                .padding(8.dp)
        )
        Text(
            "colorBlue",
            modifier = Modifier
                .fillMaxWidth()
                .background(CustomTheme.colors.colorBlue)
                .padding(8.dp)
        )
        Text(
            "colorGray",
            modifier = Modifier
                .fillMaxWidth()
                .background(CustomTheme.colors.colorGray)
                .padding(8.dp)
        )
        Text(
            "colorGray",
            modifier = Modifier
                .fillMaxWidth()
                .background(CustomTheme.colors.colorGrayLight)
                .padding(8.dp)
        )
        Text(
            "White", modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(8.dp)
        )
        Text(
            "backPrimary",
            modifier = Modifier
                .fillMaxWidth()
                .background(CustomTheme.colors.backPrimary)
                .padding(8.dp),
            color = CustomTheme.colors.labelPrimary
        )
        Text(
            "backSecondary",
            modifier = Modifier
                .fillMaxWidth()
                .background(CustomTheme.colors.backSecondary)
                .padding(8.dp),
            color = CustomTheme.colors.labelPrimary
        )
        Text(
            "backElevated",
            modifier = Modifier
                .fillMaxWidth()
                .background(CustomTheme.colors.backElevated)
                .padding(8.dp),
            color = CustomTheme.colors.labelPrimary
        )
    }
}


@Composable
@Preview(name = "Light Theme Colors")
fun ThemePreview() {
    CustomTheme {
        ThemePreviewFun()
    }
}

@Composable
@Preview(name = "Dark Theme Colors", uiMode = Configuration.UI_MODE_NIGHT_YES)
fun ThemePreviewDark() {
    CustomTheme {
        ThemePreviewFun()
    }
}