package UI

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import kotlinx.coroutines.flow.collectLatest

@Composable
fun Modifier.bouncyClickable (
    toScale : Float = 0.8f,
    onClick : () -> Unit = {}
) : Modifier {

    val interactionSource = remember {
        MutableInteractionSource()
    }
    var isPressed by remember {
        mutableStateOf(false)
    }

    val scaleAnimValue by animateFloatAsState(
        targetValue = if (isPressed) toScale else 1f,
    )

    LaunchedEffect(interactionSource) {
        interactionSource.interactions.collectLatest { interaction->

            when(interaction) {
                is PressInteraction.Press -> {
                    isPressed = true
                }
                is PressInteraction.Release -> {
                    isPressed = false
                    // TODO
                    onClick()
                }
                is PressInteraction.Cancel -> {
                    isPressed = false
                }

            }
        }
    }

    return this.then(
        Modifier
            .scale(scaleAnimValue)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = {} // Click is handled in detectTapGestures
            )
    )
}