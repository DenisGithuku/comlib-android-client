package com.githukudenis.comlib.feature.genre_setup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibButton
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibOutlinedButton
import com.githukudenis.comlib.core.designsystem.ui.components.loading_indicators.CLibCircularProgressBar
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens

@Composable
fun GenreSetupScreen(
    modifier: Modifier = Modifier,
    viewModel: GenreSetupViewModel = hiltViewModel(),
    onSkip: () -> Unit
) {
    val state by viewModel.state.collectAsState()

    GenreSetupContent(
        state = state,
        onRefresh = viewModel::onRefresh,
        onCompleteSetup = viewModel::onCompleteSetup,
        onSkip = onSkip,
        onToggleGenreSelection = viewModel::onToggleGenreSelection
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun GenreSetupContent(
    state: GenreSetupUiState,
    onRefresh: () -> Unit,
    onCompleteSetup: () -> Unit,
    onSkip: () -> Unit,
    onToggleGenreSelection: (String) -> Unit
) {
    if (state.isLoading) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(LocalDimens.current.extraLarge),
            contentAlignment = Alignment.Center
        ) {
            CLibCircularProgressBar()
        }
        return
    }

    if (state.error?.isNotEmpty() == true) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LocalDimens.current.extraLarge),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = state.error,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(
                    alpha = 0.7f
                )
            )
            Spacer(modifier = Modifier.height(LocalDimens.current.medium))
            CLibOutlinedButton(onClick = onRefresh) {
                Text(
                    text = stringResource(id = R.string.retry_btn_label),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(
                        alpha = 0.7f
                    )
                )
            }
        }
        return
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(LocalDimens.current.extraLarge)
    ) {
        LazyColumn(
            modifier = Modifier.padding(
                top = LocalDimens.current.extraLarge,
                bottom = LocalDimens.current.extraLarge * 3
            ), verticalArrangement = Arrangement.spacedBy(LocalDimens.current.large)
        ) {
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(id = R.string.genre_setup_title),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                    )
                    Box(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.07f),
                                shape = CircleShape
                            )
                            .clickable(onClick = onSkip), contentAlignment = Alignment.Center
                    ) {
                        Text(
                            modifier = Modifier.padding(
                                vertical = LocalDimens.current.small,
                                horizontal = LocalDimens.current.medium
                            ),
                            text = stringResource(id = R.string.skip_label),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                        )
                    }
                }
            }
            item {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.medium),
                    verticalArrangement = Arrangement.spacedBy(LocalDimens.current.medium),
                ) {
                    state.genres.map { item ->
                        SelectableGenreComponent(
                            selectableGenreItem = item, onToggleSelection = onToggleGenreSelection
                        )
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            CLibButton(
                modifier = Modifier.fillMaxWidth(),
                enabled = state.screenIsValid,
                onClick = onCompleteSetup,
            ) {
                Text(
                    text = stringResource(id = R.string.complete_btn_label),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}