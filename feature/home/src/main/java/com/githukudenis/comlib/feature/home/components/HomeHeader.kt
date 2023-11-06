package com.githukudenis.comlib.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeHeader(
    modifier: Modifier = Modifier,
    title: @Composable () -> Unit,
    subtitle: @Composable () -> Unit,
    profileImage: @Composable () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            title()
            subtitle()
        }
        profileImage()
    }
}