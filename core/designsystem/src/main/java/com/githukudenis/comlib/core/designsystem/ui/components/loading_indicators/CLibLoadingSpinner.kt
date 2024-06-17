
/*
* Copyright 2023 Denis Githuku
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.githukudenis.comlib.core.designsystem.ui.components.loading_indicators

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun CLibLoadingSpinner(
    color: Color = MaterialTheme.colorScheme.secondary,
    circleSize: Dp = 70.dp,
    animationDelay: Int = 3000
) {
    val circles = remember {
        listOf(
            Animatable(initialValue = 0f),
            Animatable(initialValue = 0f),
            Animatable(initialValue = 0f)
        )
    }

    circles.forEachIndexed { index, animatable ->
        /*
         * use a coroutine to sync the animation
         * divide the animation delay by the number of the circles to get delay for each circle
         *
         */
        LaunchedEffect(Unit) {
            delay(timeMillis = (animationDelay / 3L) * (index + 1))

            animatable.animateTo(
                targetValue = 1f,
                animationSpec =
                    infiniteRepeatable(
                        animation = tween(durationMillis = animationDelay, easing = LinearEasing),
                        repeatMode = RepeatMode.Restart
                    )
            )
        }
    }

    /*
    outer circle
     */
    Box(modifier = Modifier.size(circleSize).background(color = Color.Transparent)) {
        /*
        Animate the circles
         */
        circles.forEachIndexed { _, animatable ->
            Box(
                modifier =
                    Modifier.scale(scale = animatable.value)
                        .size(size = circleSize)
                        .clip(CircleShape)
                        .background(color = color.copy(alpha = (1 - animatable.value)))
            )
        }
    }
}
