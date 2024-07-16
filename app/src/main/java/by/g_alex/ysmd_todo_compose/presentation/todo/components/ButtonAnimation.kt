package by.g_alex.ysmd_todo_compose.presentation.todo.components

import androidx.annotation.FloatRange
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput

enum class ButtonState { Pressed, Idle }

/**
 * Bounce animation on click
 * Default scale = 0.9f
 */
fun Modifier.bounceClick() = bounceClick(0.9f, 0.9f)


/**
 * Bounce animation on click
 *
 * @param scale scale of button on click on X and Y. Min 0.1f
 */
fun Modifier.bounceClick(@FloatRange(0.1) scale: Float) = bounceClick(scale, scale)

/**
 * Bounce animation on click
 *
 * @param scaleToX scale of button on click on X. Min 0.1f
 * @param scaleToY scale of button on click on Y. Min 0.1f
 */

fun Modifier.bounceClick(
    @FloatRange(0.1) scaleToX: Float,
    @FloatRange(0.1) scaleToY: Float) =
    composed {
        var buttonState by remember { mutableStateOf(ButtonState.Idle) }
        val scaleStateX by animateFloatAsState(
            if (buttonState == ButtonState.Pressed) scaleToX else 1f,
            label = ""
        )
        val scaleStateY by animateFloatAsState(
            if (buttonState == ButtonState.Pressed) scaleToY else 1f,
            label = ""
        )

        this
            .graphicsLayer {
                scaleX = scaleStateX
                scaleY = scaleStateY
            }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { }
            )
            .pointerInput(buttonState) {
                awaitPointerEventScope {
                    buttonState = if (buttonState == ButtonState.Pressed) {
                        waitForUpOrCancellation()
                        ButtonState.Idle
                    } else {
                        awaitFirstDown(false)
                        ButtonState.Pressed
                    }
                }
            }
    }