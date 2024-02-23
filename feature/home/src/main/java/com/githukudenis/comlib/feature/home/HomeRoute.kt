package com.githukudenis.comlib.feature.home

import android.content.Context
import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
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
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibOutlinedButton
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibTextButton
import com.githukudenis.comlib.core.designsystem.ui.components.dialog.CLibMinimalDialog
import com.githukudenis.comlib.core.domain.usecases.FormatDateUseCase
import com.githukudenis.comlib.core.domain.usecases.TimePeriod
import com.githukudenis.comlib.core.model.book.BookMilestone
import com.githukudenis.comlib.feature.home.components.BookCard
import com.githukudenis.comlib.feature.home.components.EmptyDataCard
import com.githukudenis.comlib.feature.home.components.ErrorCard
import com.githukudenis.comlib.feature.home.components.GoalCard
import com.githukudenis.comlib.feature.home.components.HomeHeader
import com.githukudenis.comlib.feature.home.components.LoadingBookCard
import com.githukudenis.comlib.feature.home.components.SelectableBookItem
import com.githukudenis.comlib.feature.home.util.StreakProgressCalculator
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
    val userProfileState by viewModel.userProfileState.collectAsStateWithLifecycle()
    val booksState by viewModel.booksState.collectAsStateWithLifecycle()
//    val networkState by viewModel.networkStatus.collectAsStateWithLifecycle()
    val streakState by viewModel.streakState.collectAsStateWithLifecycle()
    val timePeriodState by viewModel.timePeriodState.collectAsStateWithLifecycle()

    val onRefresh = { viewModel.onEvent(HomeUiEvent.Refresh) }

    HomeScreen(booksState = booksState,
//        networkState = networkState,
        userProfileState = userProfileState,
        streakState = streakState,
        timePeriodState = timePeriodState,
        onRefresh = onRefresh,
        onOpenBookDetails = onOpenBookDetails,
        onOpenBookList = onOpenBookList,
        onOpenProfile = onOpenProfile,
        onSaveStreak = { book -> viewModel.onEvent(HomeUiEvent.SaveStreak(book)) })
}

@Composable
private fun HomeScreen(
    booksState: BooksState,
//    networkState: NetworkStatus,
    userProfileState: UserProfileState,
    streakState: StreakState,
    timePeriodState: TimePeriod,
    onRefresh: () -> Unit,
    onOpenBookDetails: (String) -> Unit,
    onOpenBookList: () -> Unit,
    onOpenProfile: () -> Unit,
    onSaveStreak: (BookMilestone) -> Unit
) {
//    when (networkState) {
//
//        NetworkStatus.Lost,
//        NetworkStatus.Unavailable,
//        NetworkStatus.Losing, -> Unit
////        -> {
////            ErrorScreen(isNetworkError = true,
////                error = stringResource(id = R.string.no_network_desc),
////                onRetry = {})
////        }
//
//        NetworkStatus.Available -> {
            LoadedScreen(
                booksState = booksState,
                userProfileState = userProfileState,
                timePeriod = timePeriodState,
                streakState = streakState,
                onOpenBookDetails = onOpenBookDetails,
                onOpenBookList = onOpenBookList,
                onOpenProfile = onOpenProfile,
                onSaveStreak = onSaveStreak
            )
//        }
//    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadedScreen(
    timePeriod: TimePeriod,
    booksState: BooksState,
    streakState: StreakState,
    userProfileState: UserProfileState,
    onOpenBookDetails: (String) -> Unit,
    onOpenBookList: () -> Unit,
    onOpenProfile: () -> Unit,
    onSaveStreak: (BookMilestone) -> Unit,
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
    var openSheetOnNewStreak: Boolean by remember { mutableStateOf(false) }
    var openSheetOnStreakDetails: Boolean by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    if (showBottomSheet) {
        ModalBottomSheet(modifier = Modifier.nestedScroll(rememberNestedScrollInteropConnection()),
            sheetState = modalBottomSheetState,
            onDismissRequest = {
                showBottomSheet = false
                openSheetOnNewStreak = false
                openSheetOnStreakDetails = false
            }) {
            if (openSheetOnNewStreak) {
                NewStreakContent(
                    onCloseBottomSheet = {
                        coroutineScope.launch {
                            modalBottomSheetState.hide()
                        }.invokeOnCompletion {
                            showBottomSheet = false
                            openSheetOnNewStreak = false
                        }
                    },
                    streakStartDate = streakStartDate,
                    streakEndDate = streakEndDate,
                    toggleDateRangeDialog = {
                        showDateRangePickerDialog = true
                    },
                    booksState = booksState,
                    context = context,
                    onSaveStreak = onSaveStreak
                )
            }
            if (openSheetOnStreakDetails) {
                StreakDetails(onDismissBottomSheet = {
                    coroutineScope.launch {
                        modalBottomSheetState.hide()
                    }.invokeOnCompletion {
                        showBottomSheet = false
                        openSheetOnStreakDetails = false
                    }
                })
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
                dateRange = buildString {
                    append(
                        FormatDateUseCase().invoke(
                            dateLong = streakState.bookMilestone?.startDate ?: 0L,
                            pattern = stringResource(R.string.month_date_pattern)
                        )
                    )
                    append(" - ")
                    append(
                        FormatDateUseCase().invoke(
                            dateLong = streakState.bookMilestone?.endDate ?: 0L,
                            pattern = stringResource(R.string.month_date_pattern)
                        )
                    )
                },
                currentBook = streakState.bookMilestone?.bookName,
                progress = if (streakState.bookMilestone == null) {
                    0f
                } else {
                    StreakProgressCalculator().invoke(
                        startDate = streakState.bookMilestone.startDate ?: return@item,
                        endDate = streakState.bookMilestone.endDate ?: return@item
                    )
                },
                hasStreak = streakState.bookMilestone != null,
                onSetStreak = {
                    showBottomSheet = true
                    openSheetOnNewStreak = true
                },
                bookId = streakState.bookMilestone?.bookId,
                onOpenStreakDetails = { bookId ->
                    showBottomSheet = true
                    openSheetOnStreakDetails = true
                })
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
        item {
            Text(
                text = "Books",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(16.dp)

            )
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
fun ErrorScreen(
    isNetworkError: Boolean, error: String, onRetry: () -> Unit
) {

    if (isNetworkError) {
        CLibMinimalDialog(title = stringResource(id = R.string.no_network_title),
            text = stringResource(id = R.string.no_network_desc),
            onDismissRequest = { })
        return
    }

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

@Composable
private fun NewStreakContent(
    streakStartDate: Long?,
    streakEndDate: Long?,
    context: Context,
    booksState: BooksState,
    onCloseBottomSheet: () -> Unit,
    onSaveStreak: (BookMilestone) -> Unit,
    toggleDateRangeDialog: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp, start = 12.dp, end = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(modifier = Modifier.align(Alignment.End), onClick = {
            onCloseBottomSheet()
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
                    text = "Start",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                if (streakStartDate != null) {
                    Text(
                        text = FormatDateUseCase().invoke(
                            dateLong = streakStartDate,
                            pattern = "dd/MM/yyyy",
                        ), style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            Column {
                Text(
                    text = "End",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
                if (streakEndDate != null) {
                    Text(
                        text = FormatDateUseCase().invoke(
                            dateLong = streakEndDate,
                            pattern = "dd/MM/yyyy",
                        ), style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
            CLibOutlinedButton(
                onClick = { toggleDateRangeDialog(true) },
                shape = MaterialTheme.shapes.extraLarge,
            ) {
                Text(
                    text = "Change"
                )
            }
        }
        when (booksState) {
            is BooksState.Loading -> {
                CLibLoadingSpinner()
            }

            is BooksState.Success -> {
                var selectedBookInStreak by remember { mutableStateOf(booksState.available.first()) }

                LazyRow {
                    items(booksState.available, key = { it.id }) { book ->
                        SelectableBookItem(book = book,
                            isSelected = book.id == selectedBookInStreak.id,
                            onSelected = {
                                selectedBookInStreak = book
                            })
                    }
                }
                CLibButton(modifier = Modifier.fillMaxWidth(), onClick = {
                    onSaveStreak(
                        BookMilestone(
                            bookId = selectedBookInStreak.id,
                            startDate = streakStartDate ?: Date().toInstant().toEpochMilli(),
                            endDate = streakEndDate ?: OffsetDateTime.now().plusDays(8).toInstant()
                                .toEpochMilli(),
                            bookName = selectedBookInStreak.title,
                        )
                    ).also {
                        Toast.makeText(
                            context,
                            context.getString(R.string.streak_save_success),
                            Toast.LENGTH_SHORT
                        ).show()
                        onCloseBottomSheet()
                    }
                }) {
                    Text(
                        text = "Save"
                    )
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

@Composable
private fun StreakDetails(
    onDismissBottomSheet: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp, start = 12.dp, end = 12.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        IconButton(onClick = onDismissBottomSheet) {
            Icon(
                imageVector = Icons.Default.Close, contentDescription = "Close"
            )
        }
        Text(
            text = "Just some test"
        )
    }
}

@Composable
fun NonFlickeringScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "This is a non-flickering screen",
            modifier = Modifier.padding(24.dp),
            style = MaterialTheme.typography.titleMedium
        )
    }
}
