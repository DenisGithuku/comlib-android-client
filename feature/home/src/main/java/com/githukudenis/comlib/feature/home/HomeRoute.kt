package com.githukudenis.comlib.feature.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.rememberNestedScrollInteropConnection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.githukudenis.comlib.core.designsystem.ui.components.CLibLoadingSpinner
import com.githukudenis.comlib.core.designsystem.ui.components.SectionSeparator
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibButton
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibTextButton
import com.githukudenis.comlib.core.designsystem.ui.components.dialog.CLibMinimalDialog
import com.githukudenis.comlib.core.domain.usecases.FormatDateUseCase
import com.githukudenis.comlib.core.domain.usecases.TimePeriod
import com.githukudenis.comlib.feature.home.components.BookCard
import com.githukudenis.comlib.feature.home.components.EmptyDataCard
import com.githukudenis.comlib.feature.home.components.ErrorCard
import com.githukudenis.comlib.feature.home.components.GoalCard
import com.githukudenis.comlib.feature.home.components.HomeHeader
import com.githukudenis.comlib.feature.home.components.LoadingBookCard
import com.githukudenis.comlib.feature.home.components.SelectableBookItem
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.OffsetDateTime
import java.util.Date

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    onOpenBookDetails: (String) -> Unit,
    onOpenBookList: () -> Unit,
    onOpenProfile: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val showNetworkDialog by viewModel.showNetworkDialog.collectAsStateWithLifecycle()
    val onRefresh = { viewModel.onEvent(HomeUiEvent.Refresh) }

    if (showNetworkDialog) {
        CLibMinimalDialog(title = stringResource(id = R.string.no_network_title),
            text = stringResource(id = R.string.no_network_desc),
            onDismissRequest = { viewModel.onDismissNetworkDialog() })
        return
    }


    HomeScreen(
        state = state,
        onRefresh = onRefresh,
        onOpenBookDetails = onOpenBookDetails,
        onOpenBookList = onOpenBookList,
        onOpenProfile = onOpenProfile
    )
}

@Composable
private fun HomeScreen(
    state: HomeUiState,
    onRefresh: () -> Unit,
    onOpenBookDetails: (String) -> Unit,
    onOpenBookList: () -> Unit,
    onOpenProfile: () -> Unit
) {
    when (state) {
        is HomeUiState.Error -> ErrorScreen(error = state.message, onRetry = onRefresh)
        HomeUiState.Loading -> LoadingScreen()
        is HomeUiState.Success -> LoadedScreen(
            booksState = state.booksState,
            userProfileState = state.userProfileState,
            timePeriod = state.timePeriod,
            onOpenBookDetails = onOpenBookDetails,
            onOpenBookList = onOpenBookList,
            onOpenProfile = onOpenProfile
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadedScreen(
    timePeriod: TimePeriod,
    booksState: BooksState,
    userProfileState: UserProfileState,
    onOpenBookDetails: (String) -> Unit,
    onOpenBookList: () -> Unit,
    onOpenProfile: () -> Unit
) {
    var showBottomSheet by remember { mutableStateOf(false) }
    val modalBottomSheetState = rememberModalBottomSheetState()
    var showDateRangePickerDialog by remember { mutableStateOf(false) }
    var streakStartDate: Long? by remember { mutableStateOf(Instant.now().toEpochMilli()) }
    var streakEndDate: Long? by remember {
        mutableStateOf(
            OffsetDateTime.now().plusDays(8).toInstant().toEpochMilli()
        )
    }
    val coroutineScope = rememberCoroutineScope()

    if (showBottomSheet) {
        ModalBottomSheet(modifier = Modifier.nestedScroll(rememberNestedScrollInteropConnection()),
            sheetState = modalBottomSheetState,
            onDismissRequest = { showBottomSheet = false }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp, start = 12.dp, end = 12.dp)
            ) {
                IconButton(modifier = Modifier.align(Alignment.End), onClick = {
                    coroutineScope.launch {
                        modalBottomSheetState.hide()
                    }.invokeOnCompletion {
                        showBottomSheet = false
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Start date",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        if (streakStartDate != null) {
                            Text(
                                text = FormatDateUseCase().invoke(
                                    streakStartDate ?: Date().toInstant().toEpochMilli()
                                ), style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    Column {
                        Text(
                            text = "End date",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Bold
                        )
                        if (streakEndDate != null) {
                            Text(
                                text = FormatDateUseCase().invoke(
                                    streakEndDate ?: OffsetDateTime.now().plusDays(8).toInstant()
                                        .toEpochMilli()
                                ), style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
                when (booksState) {
                    is BooksState.Loading -> {
                        CLibLoadingSpinner()
                    }

                    is BooksState.Success -> {
                        var selectedBookInStreak by remember { mutableStateOf(booksState.available.first().id) }

                        LazyColumn {
                            items(booksState.available, key = { it.id }) { book ->
                                SelectableBookItem(
                                    book = book,
                                    isSelected = book.id == selectedBookInStreak,
                                    onSelected = {
                                        selectedBookInStreak = book.id
                                    })
                            }
                        }
                    }

                    is BooksState.Error -> {
                        Text(text = booksState.message)
                    }

                    is BooksState.Empty -> {
                        Text(text = "No books available")
                    }
                }
            }
        }
    }

    if (showDateRangePickerDialog) {
        val dateRangePickerState = rememberDateRangePickerState(
            initialSelectedStartDateMillis = Instant.now().toEpochMilli(),
            initialSelectedEndDateMillis = OffsetDateTime.now().plusDays(8).toInstant()
                .toEpochMilli(),
            yearRange = IntRange(2024, 2100),
            initialDisplayMode = DisplayMode.Picker
        )
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.End
        ) {
            DatePickerDialog(shape = MaterialTheme.shapes.medium, onDismissRequest = {
                showDateRangePickerDialog = false
            }, confirmButton = { }) {
                DateRangePicker(
                    state = dateRangePickerState,
                    modifier = Modifier.weight(1f) // Important to display the button
                )
                CLibTextButton(
                    onClick = {
                        streakStartDate = dateRangePickerState.selectedStartDateMillis
                        streakEndDate = dateRangePickerState.selectedEndDateMillis
                        showDateRangePickerDialog = false
                    }, enabled = dateRangePickerState.selectedStartDateMillis != null
                ) {
                    Text(text = "Okay")
                }
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                paddingValues = PaddingValues(
                    top = WindowInsets.systemBars
                        .asPaddingValues()
                        .calculateTopPadding(),
                    bottom = WindowInsets.systemBars
                        .asPaddingValues()
                        .calculateBottomPadding() + 80.dp
                )
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            HomeHeader(modifier = Modifier.padding(horizontal = 16.dp), title = {
                Text(
                    text = buildString {
                        append("Good")
                        append(" ")
                        append(timePeriod.name.lowercase()
                            .replaceFirstChar { char -> char.uppercase() })
                        append(" ")
                        append(when (userProfileState) {
                            is UserProfileState.Error -> "Stranger"
                            UserProfileState.Loading -> "Stranger"
                            is UserProfileState.Success -> {
                                (userProfileState.user?.firstname
                                    ?: "stranger").replaceFirstChar { char -> char.uppercase() }
                            }
                        })
                    }, style = MaterialTheme.typography.titleSmall
                )
            }, subtitle = {
                Text(
                    text = stringResource(id = R.string.home_header_subtitle),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            }, profileImage = {
                AsyncImage(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .clickable {
                            onOpenProfile()
                        }, model = when (userProfileState) {
                        is UserProfileState.Error -> "https://comlib-api.onrender.com/img/users/default_img.jpg"
                        UserProfileState.Loading -> "https://comlib-api.onrender.com/img/users/default_img.jpg"
                        is UserProfileState.Success -> "https://comlib-api.onrender.com/img/users/${userProfileState.user?.image}"
                    }, contentDescription = "User profile"
                )
            })
        }
        item {
            GoalCard(modifier = Modifier.padding(horizontal = 16.dp),
                dateRange = "Aug 29 - Sep 23",
                currentBook = "Philosopher's Stone",
                progress = 0.45f,
                hasStreak = false,
                onSetStreak = { showBottomSheet = true })
        }
        item {
            AnimatedVisibility(
                visible = when (booksState) {
                    BooksState.Empty -> false
                    is BooksState.Error -> false
                    BooksState.Loading -> false
                    is BooksState.Success -> booksState.readBooks.isNotEmpty()
                }
            ) {
                SectionSeparator(modifier = Modifier.padding(16.dp),
                    title = stringResource(id = R.string.already_read_separator),
                    onViewAll = {})
                Spacer(modifier = Modifier.height(8.dp))
                when (booksState) {
                    BooksState.Empty -> EmptyDataCard(content = "read books")
                    is BooksState.Error -> ErrorCard(content = "Could not find read books")
                    BooksState.Loading -> {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(4) {
                                LoadingBookCard()
                            }
                        }
                    }

                    is BooksState.Success -> {
                        LazyRow(
                            contentPadding = PaddingValues(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(booksState.readBooks, key = { book -> book._id }) { book ->
                                BookCard(book = book, onClick = onOpenBookDetails)
                            }
                        }
                    }
                }
            }
        }
        item {
            SectionSeparator(
                modifier = Modifier.padding(horizontal = 16.dp),
                title = stringResource(id = R.string.available_books_separator),
                onViewAll = onOpenBookList
            )
        }

        item {
            when (booksState) {
                is BooksState.Error -> ErrorCard(content = booksState.message)
                BooksState.Loading -> {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(4) {
                            LoadingBookCard()
                        }
                    }
                }

                is BooksState.Success -> {
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(booksState.available, key = { book -> book._id }) { book ->
                            BookCard(book = book, onClick = onOpenBookDetails)
                        }
                    }
                }

                BooksState.Empty -> EmptyDataCard(content = "books")
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CLibLoadingSpinner()
    }
}

@Composable
fun ErrorScreen(error: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart
    ) {
        Column(modifier = Modifier.padding(24.dp)) {
            Text(
                text = error,
                style = MaterialTheme.typography.titleMedium,
            )
            Spacer(modifier = Modifier.height(16.dp))
            CLibButton(onClick = onRetry) {
                Text(
                    text = stringResource(id = R.string.retry),
                )
            }
        }
    }
}
