
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
package com.githukudenis.comlib

import android.view.animation.OvershootInterpolator
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.coroutines.delay

const val splashScreenRoute = "splash_screen"
const val SplashWaitMillis = 3000L

fun NavGraphBuilder.splashScreen(onTimeout: () -> Unit) {
    composable(route = splashScreenRoute, exitTransition = { fadeOut() }) {
        SplashScreenRoute(waitMillis = SplashWaitMillis, onTimeout = onTimeout)
    }
}

@Composable
fun SplashScreenRoute(waitMillis: Long, onTimeout: () -> Unit) {
    Box(
        modifier =
            Modifier.navigationBarsPadding()
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
    ) {
        val scale = remember { Animatable(0f) }

        val currentOnTimeout by rememberUpdatedState(onTimeout)

        LaunchedEffect(true) {
            scale.animateTo(
                targetValue = 0.7f,
                animationSpec =
                    tween(durationMillis = 1000, easing = { OvershootInterpolator(2f).getInterpolation(it) })
            )
            delay(waitMillis)
            currentOnTimeout()
        }

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = stringResource(R.string.app_logo),
            modifier = Modifier.scale(scale = scale.value).align(Alignment.Center)
        )
        Text(
            text = stringResource(id = R.string.app_name),
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 24.dp),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}
