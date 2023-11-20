package com.githukudenis.comlib.core.designsystem.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun SectionSeparator(
    modifier: Modifier = Modifier, title: String, count: String, onViewAll: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )
        Row(
            modifier = Modifier.clickable(
                interactionSource = interactionSource,
                enabled = true,
                indication = null,
                onClick = { onViewAll() }),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "View all", style = MaterialTheme.typography.labelSmall
            )
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.extraLarge)
                    .background(
                        MaterialTheme.colorScheme.surfaceVariant
                    ), contentAlignment = Alignment.Center
            ) {
                Text(
                    modifier = Modifier.padding(
                        horizontal = 8.dp, vertical = 4.dp
                    ), text = "+ $count", style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}