package com.githukudenis.comlib.core.designsystem.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val default: Dp = 0.dp,
    val extraSmall: Dp = 0.dp,
    val small: Dp = 0.dp,
    val medium: Dp = 0.dp,
    val large: Dp = 0.dp,
    val extraLarge: Dp = 0.dp
)

val LocalDimens = staticCompositionLocalOf {
    Dimens()
}