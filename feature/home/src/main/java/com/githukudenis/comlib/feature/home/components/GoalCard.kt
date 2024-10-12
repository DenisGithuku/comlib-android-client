
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
package com.githukudenis.comlib.feature.home.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibButton
import com.githukudenis.comlib.feature.home.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalCard(
    hasStreak: Boolean,
    onOpenStreakDetails: (String?) -> Unit,
    dateRange: String? = null,
    currentBookTitle: String? = null,
    bookId: String? = null,
    progress: Float? = null
) {
    Card(
        modifier = Modifier.padding(horizontal = 16.dp),
        onClick = { onOpenStreakDetails(bookId) },
        shape = MaterialTheme.shapes.small,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
        border =
            BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f))
    ) {
        AnimatedContent(targetState = hasStreak) { hasStreak ->
            if (hasStreak) {
                Column(
                    modifier = Modifier.padding(12.dp).fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_electric),
                                contentDescription = stringResource(id = R.string.see_details),
                                tint = Color(0xFFFBBC05)
                            )
                            Text(text = "Streak", style = MaterialTheme.typography.titleSmall)
                        }
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Details",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                            )
                            IconButton(onClick = { onOpenStreakDetails(bookId) }) {
                                Icon(
                                    modifier = Modifier.size(16.dp),
                                    painter = painterResource(id = R.drawable.ic_chevron_right),
                                    contentDescription = stringResource(id = R.string.see_details),
                                    tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                                )
                            }
                        }
                    }
                    if (currentBookTitle != null) {
                        Text(
                            text = currentBookTitle,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                        )
                    }
                    if (progress != null) {
                        LinearProgressIndicator(
                            progress = progress,
                            color = MaterialTheme.colorScheme.secondary,
                            trackColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                            strokeCap = StrokeCap.Round,
                            modifier = Modifier.fillMaxWidth().height(2.dp)
                        )

                        Text(
                            text = "${(progress * 100).toInt()}%",
                            style = MaterialTheme.typography.titleMedium,
                            color = MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.no_streak_label),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                    CLibButton(onClick = { onOpenStreakDetails(null) }) { Text(text = "Start streak") }
                }
            }
        }
    }
}
