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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import com.githukudenis.comlib.core.model.book.Book
import kotlinx.datetime.LocalDate

@Composable
fun StreakScreen(
    viewModel: StreakViewModel = hiltViewModel(), onNavigateUp: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    StreakContent(
        state = state,
        onSaveStreak = viewModel::onSaveStreak,
        onNavigateUp = onNavigateUp,
        onToggleBook = viewModel::onToggleBook
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StreakContent(
    state: StreakUiState,
    onSaveStreak: () -> Unit,
    onNavigateUp: () -> Unit,
    onToggleBook: (Book?) -> Unit
) {

    var bottomSheetIsVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var showDatePicker by rememberSaveable {
        mutableStateOf(false)
    }

    var isStartDatePickerState by rememberSaveable {
        mutableStateOf(false)
    }
    var isEndDatePickerState by rememberSaveable {
        mutableStateOf(false)
    }

    if (bottomSheetIsVisible) {
        ModalBottomSheet(onDismissRequest = { bottomSheetIsVisible = false }) {
            if (state.isLoading) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(LocalDimens.current.extraLarge),
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = LocalDimens.current.medium),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(onClick = { bottomSheetIsVisible = false }) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f),
                                contentDescription = stringResource(R.string.close),
                            )
                        }
                    }
                }
                items(state.availableBooks, key = { it.id }) { book ->
                    SelectableStreakBook(
                        book = book,
                        isSelected = book.id == state.selectedBook?.id,
                        onSelect = onToggleBook
                    )
                }
            }
        }
    }

    if (showDatePicker) {
//        DatePicker(state = DatePickerState(
//            initialSelectedDateMillis = state.startDate,
//            initialDisplayedMonthMillis = state.startDate.month
//        ))
    }

    Scaffold(topBar = {
        CenterAlignedTopAppBar(title = {
            Text(
                text = stringResource(id = R.string.streak_details_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
            )
        }, navigationIcon = {
            IconButton(onClick = onNavigateUp) {
                Icon(
                    imageVector = Icons.Default.ArrowBack, contentDescription = stringResource(
                        R.string.back
                    )
                )
            }
        }, actions = {
            IconButton(onClick = onSaveStreak, enabled = state.isValid) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = stringResource(R.string.save_streak_label)
                )
            }
        })
    }) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.spacedBy(LocalDimens.current.large)
        ) {
            SelectedBook(book = state.selectedBook, onAddBook = {
                bottomSheetIsVisible = true
            }, onDeleteBook = { onToggleBook(null) })
            DateRow(startDate = state.startDate, endDate = state.endDate, onChangeStartDate = {
                showDatePicker = true
                isStartDatePickerState = true
            }, onChangeEndDate = {
                showDatePicker = true
                isEndDatePickerState = true
            })
        }
    }
}

@Composable
private fun SelectedBook(book: Book?, onAddBook: () -> Unit, onDeleteBook: () -> Unit) {
    AnimatedContent(targetState = book == null) { isNull ->
        if (isNull) {
            Column(modifier = Modifier
                .fillMaxWidth()
                .clickable { onAddBook() }
                .padding(
                    LocalDimens.current.extraLarge
                ), verticalArrangement = Arrangement.spacedBy(LocalDimens.current.small)) {
                Text(
                    text = "No book selected",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                )
                CLibTextButton(onClick = onAddBook) {
                    Text(
                        text = "Select",
                    )
                }
            }
        } else {
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable { onAddBook() }
                .padding(LocalDimens.current.extraLarge),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween) {
                Column {
                    book?.let {
                        Text(
                            text = book.title,
                            style = MaterialTheme.typography.titleSmall,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                        )
                        Spacer(modifier = Modifier.height(LocalDimens.current.medium))
                        Text(
                            text = "Pages: ${book.pages}",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.8f)
                        )
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
    book: Book, isSelected: Boolean, onSelect: (Book) -> Unit
) {
    Row(modifier = Modifier
        .fillMaxWidth()
        .clickable { onSelect(book) }
        .padding(LocalDimens.current.medium),
        horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.medium),
        verticalAlignment = Alignment.CenterVertically) {
        RadioButton(selected = isSelected, onClick = { onSelect(book) })
        Text(
            text = book.title,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun DateRow(
    startDate: LocalDate,
    endDate: LocalDate,
    onChangeStartDate: () -> Unit,
    onChangeEndDate: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(LocalDimens.current.extraLarge),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        DateComponent(
            date = startDate,
            title = stringResource(id = R.string.start_label),
            onChangeDate = onChangeStartDate
        )
        DateComponent(
            date = endDate,
            title = stringResource(R.string.end_label),
            onChangeDate = onChangeEndDate
        )
    }
}

@Composable
private fun DateComponent(
    date: LocalDate, title: String, onChangeDate: () -> Unit
) {
    Column {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "$title:",
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
        CLibOutlinedButton(onClick = onChangeDate) {
            Text(
                text = stringResource(R.string.update_label)
            )
        }
    }
}

private fun LocalDate.toDayAndMonth(): String {
    return buildString {
        append(this@toDayAndMonth.dayOfMonth)
        append(" ")
        append(this@toDayAndMonth.month.name.take(3).capitalize())
    }
}