package com.githukudenis.comlib.core.designsystem.ui.components.loading_indicators

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun loadingBrush(): Brush {
    /*
    animate infinite
     */
    val infiniteTransition = rememberInfiniteTransition()

    /*
    animate brush color list
     */
    val colors = listOf(
        Color.Black.copy(alpha = 0.1f),
        Color.Black.copy(alpha = 0.08f)
    )

    val transitionAnimation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, delayMillis = 500, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    /*
    create brush with transition animation
     */
    val brush = Brush.linearGradient(
        colors = colors,
        start = Offset.Zero,
        end = Offset(x = transitionAnimation, y = transitionAnimation)
    )
    return brush
}