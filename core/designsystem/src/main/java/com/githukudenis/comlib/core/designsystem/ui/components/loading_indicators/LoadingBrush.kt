
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
    val colors = listOf(Color.Black.copy(alpha = 0.1f), Color.Black.copy(alpha = 0.08f))

    val transitionAnimation by
        infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 1000f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(durationMillis = 1000, delayMillis = 500, easing = FastOutSlowInEasing),
                    repeatMode = RepeatMode.Restart
                )
        )

    /*
    create brush with transition animation
     */
    val brush =
        Brush.linearGradient(
            colors = colors,
            start = Offset.Zero,
            end = Offset(x = transitionAnimation, y = transitionAnimation)
        )
    return brush
}
