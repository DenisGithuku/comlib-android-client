
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
package com.githukudenis.comlib.feature.streak

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.githukudenis.comlib.core.common.capitalize
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibOutlinedButton
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibTextButton
import com.githukudenis.comlib.core.designsystem.ui.components.loading_indicators.CLibCircularProgressBar
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

@Composable
fun StreakScreen(viewModel: StreakViewModel = hiltViewModel(), onNavigateUp: () -> Unit) {
    val state by viewModel.state.collectAsState()
    val currentOnSaveStreak by rememberUpdatedState(newValue = onNavigateUp)

    LaunchedEffect(key1 = state.saveSuccess) {
        if (state.saveSuccess) {
            currentOnSaveStreak()
        }
    }
    StreakContent(
        state = state,
        onSaveStreak = viewModel::onSaveStreak,
        onNavigateUp = onNavigateUp,
        onChangeStartDate = viewModel::onChangeStartDate,
        onChangeEndDate = viewModel::onChangeEndDate,
        onToggleBook = viewModel::onToggleBook
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StreakContent(
    state: StreakUiState,
    onChangeStartDate: (LocalDate) -> Unit,
    onChangeEndDate: (LocalDate) -> Unit,
    onSaveStreak: () -> Unit,
    onNavigateUp: () -> Unit,
    onToggleBook: (StreakBook?) -> Unit
) {
    var bottomSheetIsVisible by rememberSaveable { mutableStateOf(false) }

    var showDatePicker by rememberSaveable { mutableStateOf(false) }

    val yearToday = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year

    val dateRangePickerState =
        rememberDateRangePickerState(
            initialDisplayMode = DisplayMode.Input,
            initialSelectedStartDateMillis = state.startDate.toMillisLong(),
            initialSelectedEndDateMillis = state.endDate.toMillisLong(),
            yearRange = IntRange(start = yearToday, endInclusive = yearToday + 1)
        )

    if (bottomSheetIsVisible) {
        ModalBottomSheet(onDismissRequest = { bottomSheetIsVisible = false }) {
            if (state.isLoading) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(LocalDimens.current.extraLarge),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CLibCircularProgressBar()
                    Spacer(modifier = Modifier.height(LocalDimens.current.large))
                    Text(
                        text = "Fetching available books",
                        style = MaterialTheme.typography.titleSmall,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                    )
                }
                return@ModalBottomSheet
            }

            LazyColumn {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(horizontal = LocalDimens.current.medium),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = { bottomSheetIsVisible = false }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                                contentDescription = stringResource(R.string.close)
                            )
                        }
                    }
                }
                items(state.availableBooks, key = { it.id }) { book ->
                    SelectableStreakBook(
                        streakBook = book.asStreakBook(),
                        isSelected = book.id == state.selectedBook?.id,
                        onSelect = onToggleBook
                    )
                }
            }
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            modifier = Modifier.padding(LocalDimens.current.extraLarge),
            shape = MaterialTheme.shapes.extraLarge,
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val start = dateRangePickerState.selectedStartDateMillis?.toLocalDate()
                        val end = dateRangePickerState.selectedEndDateMillis?.toLocalDate()
                        start?.let { onChangeStartDate(it) }
                        end?.let { onChangeEndDate(it) }
                        showDatePicker = false
                    }
                ) {
                    Text(text = stringResource(R.string.confirm_btn_label))
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text(text = stringResource(R.string.cancel_btn_label))
                }
            }
        ) {
            DateRangePicker(
                showModeToggle = true,
                title = {
                    Text(modifier = Modifier.padding(LocalDimens.current.medium), text = "Select dates")
                },
                headline = {
                    Text(
                        modifier = Modifier.padding(LocalDimens.current.medium),
                        text = dateRangePickerState.displayMode.toString()
                    )
                },
                modifier = Modifier.padding(LocalDimens.current.extraLarge),
                state = dateRangePickerState,
                dateValidator = { timestamp -> timestamp >= Clock.System.now().toEpochMilliseconds() }
            )
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.streak_details_title),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onSaveStreak, enabled = state.isValid) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = stringResource(R.string.save_streak_label)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(LocalDimens.current.large)
        ) {
            SelectedBook(
                streakBook = state.selectedBook,
                onAddBook = { bottomSheetIsVisible = true },
                onDeleteBook = { onToggleBook(null) }
            )
            DateRow(
                startDate = state.startDate,
                endDate = state.endDate,
                onChangeDates = { showDatePicker = true }
            )
        }
    }
}

@Composable
private fun SelectedBook(streakBook: StreakBook?, onAddBook: () -> Unit, onDeleteBook: () -> Unit) {
    AnimatedContent(targetState = streakBook == null) { isNull ->
        if (isNull) {
            Column(
                modifier =
                    Modifier.fillMaxWidth().clickable { onAddBook() }.padding(LocalDimens.current.extraLarge),
                verticalArrangement = Arrangement.spacedBy(LocalDimens.current.small)
            ) {
                Text(
                    text = "No book selected",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
                CLibTextButton(onClick = onAddBook) { Text(text = "Select") }
            }
        } else {
            Row(
                modifier =
                    Modifier.fillMaxWidth().clickable { onAddBook() }.padding(LocalDimens.current.extraLarge),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    streakBook?.let {
                        streakBook.title?.let { it1 ->
                            Text(
                                text = it1,
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                            )
                        }
                        Spacer(modifier = Modifier.height(LocalDimens.current.medium))
                        streakBook.pages?.let {
                            Text(
                                text = "Pages: ${streakBook.pages}",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
                IconButton(onClick = { onDeleteBook() }) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = stringResource(id = R.string.delete_book_icon_label),
                        tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                    )
                }
            }
        }
    }
}

@Composable
private fun SelectableStreakBook(
    streakBook: StreakBook,
    isSelected: Boolean,
    onSelect: (StreakBook) -> Unit
) {
    Row(
        modifier =
            Modifier.fillMaxWidth()
                .clickable { onSelect(streakBook) }
                .padding(LocalDimens.current.medium),
        horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.medium),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(selected = isSelected, onClick = { onSelect(streakBook) })
        streakBook.title?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun DateRow(startDate: LocalDate, endDate: LocalDate, onChangeDates: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(LocalDimens.current.extraLarge),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DateComponent(date = startDate, title = stringResource(id = R.string.start_label))
        DateComponent(date = endDate, title = stringResource(R.string.end_label))
        CLibOutlinedButton(onClick = onChangeDates) {
            Text(text = stringResource(id = R.string.update_label))
        }
    }
}

@Composable
private fun DateComponent(date: LocalDate, title: String) {
    Column {
        Text(
            text = "$title",
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
        Spacer(modifier = Modifier.width(LocalDimens.current.medium))
        Text(
            text = date.toDayAndMonth(),
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
    }
}

fun LocalDate.toDayAndMonth(): String {
    return buildString {
        append(this@toDayAndMonth.dayOfMonth)
        append(" ")
        append(this@toDayAndMonth.month.name.take(3).capitalize())
    }
}

fun LocalDate.toMillisLong(): Long {
    return this.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

fun Long.toLocalDate(): LocalDate {
    val instant = Instant.fromEpochMilliseconds(this)
    return instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
}
