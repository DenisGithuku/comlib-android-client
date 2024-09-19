
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

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.githukudenis.comlib.core.designsystem.R

val PoppinsFamily =
    FontFamily(
        Font(resId = R.font.poppins_black, weight = FontWeight.Black),
        Font(resId = R.font.poppins_regular, weight = FontWeight.Normal),
        Font(resId = R.font.poppins_bold, weight = FontWeight.Bold),
        Font(resId = R.font.poppins_semibold, weight = FontWeight.SemiBold),
        Font(resId = R.font.poppins_light, weight = FontWeight.Light),
        Font(resId = R.font.poppins_medium, weight = FontWeight.Medium)
    )

// Set of Material typography styles to start with
val Typography =
    Typography(
        displayLarge =
        TextStyle(
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 57.sp,
            lineHeight = 64.sp
        ),
        displayMedium =
        TextStyle(
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 45.sp,
            lineHeight = 52.sp
        ),
        displaySmall =
        TextStyle(
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 36.sp,
            lineHeight = 44.sp
        ),
        headlineLarge =
        TextStyle(
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 32.sp,
            lineHeight = 40.sp
        ),
        headlineMedium =
        TextStyle(
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 28.sp,
            lineHeight = 36.sp
        ),
        headlineSmall =
        TextStyle(
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 24.sp,
            lineHeight = 32.sp
        ),
        titleLarge =
        TextStyle(
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.W500,
            fontSize = 22.sp,
            lineHeight = 28.sp,
            letterSpacing = 0.sp
        ),
        titleMedium =
        TextStyle(
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.W500,
            fontSize = 16.sp,
            lineHeight = 24.sp
        ),
        titleSmall =
        TextStyle(
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.W500,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
        labelLarge =
        TextStyle(
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.W500,
            fontSize = 14.sp,
            lineHeight = 20.sp
        ),
        labelMedium =
        TextStyle(
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.W500,
            fontSize = 12.sp,
            lineHeight = 16.sp
        ),
        labelSmall =
        TextStyle(
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            lineHeight = 16.sp,
            letterSpacing = 0.5.sp
        ),
        bodyLarge =
        TextStyle(
            fontFamily = PoppinsFamily,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            lineHeight = 24.sp,
            letterSpacing = 0.5.sp
        ),
        bodyMedium =
        TextStyle(
            fontWeight = FontWeight.Normal,
            fontFamily = PoppinsFamily,
            fontSize = 14.sp,
            lineHeight = 18.sp
        ),
        bodySmall =
        TextStyle(
            fontWeight = FontWeight.Normal,
            fontFamily = PoppinsFamily,
            fontSize = 12.sp,
            lineHeight = 16.sp
        )
    )