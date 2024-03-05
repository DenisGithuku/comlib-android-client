package com.githukudenis.comlib.feature.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.githukudenis.comlib.core.designsystem.ui.components.loading_indicators.loadingBrush
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens

@Composable
fun LoadingBookCard() {
    Card(
        modifier = Modifier.width(
            150.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 0.dp
        ),
        border = BorderStroke(
            width = 1.dp,
            color = MaterialTheme.colorScheme.onBackground.copy(
                alpha = 0.1f
            )
        )
    ) {
        Column(
            modifier = Modifier.padding(LocalDimens.current.large), verticalArrangement = Arrangement.spacedBy(
                LocalDimens.current.large)
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(brush = loadingBrush())
                    .align(Alignment.CenterHorizontally)
            )
            Box(
                modifier = Modifier
                    .height(LocalDimens.current.medium)
                    .fillMaxWidth(0.8f)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(brush = loadingBrush())
            )
            Box(
                modifier = Modifier
                    .height(LocalDimens.current.large)
                    .fillMaxWidth(0.6f)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(brush = loadingBrush())
            )
            Box(
                modifier = Modifier
                    .height(32.dp)
                    .fillMaxWidth()
                    .clip(CircleShape)
                    .background(brush = loadingBrush())
            )
        }
    }
}