package by.g_alex.ysmd_todo_compose.presentation.ui.theme

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

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

// Custom theme with light and dark mode support
@Composable
fun ToDoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colors = when {
        darkTheme -> darkColors
        else -> lightColors
    }

    val typography = LocalCustomTypography.current

    val dp = LocalCustomDp.current

    CompositionLocalProvider(
        LocalCustomColors provides colors,
        LocalCustomTypography provides typography,
        LocalCustomDp provides dp,
        content = content
    )
}

object ToDoTheme {
    val colors: CustomColors
        @Composable
        get() = LocalCustomColors.current
    val typography: CustomTypography
        @Composable
        get() = LocalCustomTypography.current
    val dp: CustomDp
        @Composable
        get() = LocalCustomDp.current
}

@Composable
private fun ThemePreviewFun() {
    Column {
        Text(
            "supportSeparator",
            modifier = Modifier
                .fillMaxWidth()
                .background(ToDoTheme.colors.supportSeparator)
                .padding(8.dp)
        )
        Text(
            "supportOverlay",
            modifier = Modifier
                .fillMaxWidth()
                .background(ToDoTheme.colors.supportOverlay)
                .padding(8.dp)
        )
        Text(
            "labelPrimary",
            modifier = Modifier
                .fillMaxWidth()
                .background(ToDoTheme.colors.labelPrimary)
                .padding(8.dp),
            color = ToDoTheme.colors.backPrimary
        )
        Text(
            "labelSecondary",
            modifier = Modifier
                .fillMaxWidth()
                .background(ToDoTheme.colors.labelSecondary)
                .padding(8.dp)
        )
        Text(
            "labelTertiary",
            modifier = Modifier
                .fillMaxWidth()
                .background(ToDoTheme.colors.labelTertiary)
                .padding(8.dp)
        )
        Text(
            "labelDisable",
            modifier = Modifier
                .fillMaxWidth()
                .background(ToDoTheme.colors.labelDisable)
                .padding(8.dp)
        )
        Text(
            "colorRed", modifier = Modifier
                .fillMaxWidth()
                .background(ToDoTheme.colors.colorRed)
                .padding(8.dp)
        )
        Text(
            "colorGreen",
            modifier = Modifier
                .fillMaxWidth()
                .background(ToDoTheme.colors.colorGreen)
                .padding(8.dp)
        )
        Text(
            "colorBlue",
            modifier = Modifier
                .fillMaxWidth()
                .background(ToDoTheme.colors.colorBlue)
                .padding(8.dp)
        )
        Text(
            "colorGray",
            modifier = Modifier
                .fillMaxWidth()
                .background(ToDoTheme.colors.colorGray)
                .padding(8.dp)
        )
        Text(
            "colorGray",
            modifier = Modifier
                .fillMaxWidth()
                .background(ToDoTheme.colors.colorGrayLight)
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
                .background(ToDoTheme.colors.backPrimary)
                .padding(8.dp),
            color = ToDoTheme.colors.labelPrimary
        )
        Text(
            "backSecondary",
            modifier = Modifier
                .fillMaxWidth()
                .background(ToDoTheme.colors.backSecondary)
                .padding(8.dp),
            color = ToDoTheme.colors.labelPrimary
        )
        Text(
            "backElevated",
            modifier = Modifier
                .fillMaxWidth()
                .background(ToDoTheme.colors.backElevated)
                .padding(8.dp),
            color = ToDoTheme.colors.labelPrimary
        )
    }
}


@Composable
@Preview(name = "Light Theme Colors")
private fun ThemePreview() {
    ToDoTheme {
        ThemePreviewFun()
    }
}

@Composable
@Preview(name = "Dark Theme Colors", uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ThemePreviewDark() {
    ToDoTheme {
        ThemePreviewFun()
    }
}