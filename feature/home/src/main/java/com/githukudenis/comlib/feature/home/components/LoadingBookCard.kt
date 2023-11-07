package com.githukudenis.comlib.feature.home.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoadingBookCard() {

    val animationColors = remember {
        listOf(
            Color.Black.copy(alpha = 0.1f),
            Color.Black.copy(alpha = 0.08f),
        )
    }

    val infiniteTransition = rememberInfiniteTransition()
    val transitionAnimation = infiniteTransition.animateFloat(
        initialValue = 0f, targetValue = 1000f, animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000, delayMillis = 500, easing = FastOutSlowInEasing
            ), repeatMode = RepeatMode.Restart
        )
    )

    val brush = Brush.linearGradient(
        colors = animationColors,
        start = Offset.Zero,
        end = Offset(x = transitionAnimation.value, y = transitionAnimation.value)
    )

    Card(
        modifier = Modifier.width(
            150.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(brush)
            )
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth(0.8f)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(brush)
            )
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth(0.6f)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(brush)
            )
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth(0.5f)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(brush)
            )
        }
    }
}