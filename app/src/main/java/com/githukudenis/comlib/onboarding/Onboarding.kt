
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
package com.githukudenis.comlib.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.githukudenis.comlib.R
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibButton
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens

@Composable
fun OnBoardingScreen(onGetStarted: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(horizontal = LocalDimens.current.sixteen),
        verticalArrangement = Arrangement.SpaceAround
    ) {
        OnBoardingAnimation()
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.padding(top = LocalDimens.current.sixteen),
                text =
                    stringResource(id = R.string.onboarding_text, stringResource(id = R.string.app_name)),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(LocalDimens.current.twentyFour))
            CLibButton(modifier = Modifier.fillMaxWidth(), onClick = onGetStarted) {
                Text(
                    text = stringResource(id = R.string.get_started_btn_text),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

@Composable
fun OnBoardingAnimation(modifier: Modifier = Modifier) {
    val isPlaying by remember { mutableStateOf(true) }

    val speed by remember { mutableStateOf(0.5f) }

    // rememeber lottie composition for the json
    // which accepts the lottie composition result
    val composition by
        rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.onboarding_animation))

    // to control the animation
    val progress by
        animateLottieCompositionAsState(
            // pass composition created above
            composition = composition,
            // iterates forever
            iterations = LottieConstants.IterateForever,

            // pass is playing which controls the animation play/pause
            isPlaying = isPlaying,

            // pass speed we can set it to change the speed of animation
            speed = speed,

            // restart on play if we pause and play again
            restartOnPlay = false
        )

    LottieAnimation(
        composition = composition,
        contentScale = ContentScale.Crop,
        progress = { progress },
        modifier = modifier.size(400.dp)
    )
}
