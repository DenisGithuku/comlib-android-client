
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

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun CLibCircularProgressBar(
    modifier: Modifier = Modifier,
    size: Dp = 36.dp,
    trackColor: Color = MaterialTheme.colorScheme.secondary
) {
    val infiniteTransition = rememberInfiniteTransition(label = "Infinite value progress")

    val progressValue by
        infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 5f,
            animationSpec =
                infiniteRepeatable(
                    animation = tween(durationMillis = 1500, easing = LinearEasing),
                    repeatMode = RepeatMode.Restart
                ),
            label = "Progress Value"
        )

    Canvas(modifier = modifier.size(size)) {
        drawArc(
            color = Color.Black.copy(alpha = 0.08f),
            startAngle = 90f,
            sweepAngle = 360f,
            useCenter = false,
            style = Stroke(width = 6.dp.value)
        )
        drawArc(
            color = trackColor,
            startAngle = -90f * progressValue,
            useCenter = false,
            sweepAngle = 90f,
            style = Stroke(width = 6.dp.value, cap = StrokeCap.Round)
        )
    }
}

@Preview(name = "Progress Bar Preview")
@Composable
private fun CircularProgressPrev() {
    Box(modifier = Modifier.padding(16.dp)) { CLibCircularProgressBar() }
}
