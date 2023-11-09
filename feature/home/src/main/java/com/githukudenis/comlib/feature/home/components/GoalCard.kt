package com.githukudenis.comlib.feature.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun GoalCard(
    modifier: Modifier = Modifier,
    dateRange: String, currentBook: String, progress: Float
) {
    Surface(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 0.dp,
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Current streak: $dateRange",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = currentBook,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.8f
                )
            )
            LinearProgressIndicator(
                progress = progress,
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.2f
                ),
                strokeCap = StrokeCap.Round,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = "Progress ${progress * 100}%",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}