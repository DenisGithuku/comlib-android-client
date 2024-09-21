
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
package com.githukudenis.comlib.core.designsystem.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

private val DarkColorScheme =
    darkColorScheme(
        primary = PastelGreen,
        secondary = MossGreen,
        tertiary = MossGreen,
        background = CharcoalGray,
        surface = Color(0xFF626262),
        onPrimary = SnowDrift,
        onSecondary = SnowDrift,
        onTertiary = SnowDrift,
        onBackground = SnowDrift,
        onSurface = IvoryWhisper,
        secondaryContainer = IvoryWhisper
    )

private val LightColorScheme =
    lightColorScheme(
        primary = PastelGreen,
        secondary = MossGreen,
        tertiary = LimeGreen,
        background = SnowDrift,
        surface = IvoryWhisper,
        onPrimary = Color.White,
        onSecondary = Color.White,
        onTertiary = Color.White,
        onBackground = CharcoalGray,
        onSurface = CharcoalGray,
        primaryContainer = PastelGreen,
        tertiaryContainer = LimeGreen,
        surfaceVariant = SnowDrift,
        secondaryContainer = SpringGreen
    )

@Composable
fun ComLibTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val dimens: Dimens =
        Dimens(
            default = 0.dp,
            extraSmall = 2.dp,
            small = 4.dp,
            medium = 6.dp,
            large = 12.dp,
            extraLarge = 16.dp
        )
    val colorScheme =
        when {
            dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
                val context = LocalContext.current
                if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
            }
            darkTheme -> DarkColorScheme
            else -> LightColorScheme
        }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(LocalDimens provides dimens) {
        MaterialTheme(colorScheme = colorScheme, typography = CLibTypography, content = content)
    }
}
