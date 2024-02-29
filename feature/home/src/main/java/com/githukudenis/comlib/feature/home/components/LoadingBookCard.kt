package com.githukudenis.comlib.feature.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.githukudenis.comlib.core.designsystem.ui.components.loading_indicators.loadingBrush

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
            defaultElevation = 4.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(8.dp), verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .height(120.dp)
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(brush = loadingBrush())
            )
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth(0.8f)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(brush = loadingBrush())
            )
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth(0.6f)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(brush = loadingBrush())
            )
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .fillMaxWidth(0.5f)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(brush = loadingBrush())
            )
        }
    }
}