package com.githukudenis.comlib.feature.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.githukudenis.comlib.core.designsystem.ui.components.CLibLoadingSpinner
import com.githukudenis.comlib.core.designsystem.ui.components.SectionSeparator
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibButton
import com.githukudenis.comlib.core.designsystem.ui.components.dialog.CLibMinimalDialog
import com.githukudenis.comlib.core.domain.usecases.TimePeriod
import com.githukudenis.comlib.feature.home.components.BookCard
import com.githukudenis.comlib.feature.home.components.EmptyDataCard
import com.githukudenis.comlib.feature.home.components.ErrorCard
import com.githukudenis.comlib.feature.home.components.GoalCard
import com.githukudenis.comlib.feature.home.components.HomeHeader
import com.githukudenis.comlib.feature.home.components.LoadingBookCard

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
        CLibMinimalDialog(
            title = stringResource(id = R.string.no_network_title),
            text = stringResource(id = R.string.no_network_desc),
            onDismissRequest = { viewModel.onDismissNetworkDialog() }
        )
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

@Composable
fun LoadedScreen(
    timePeriod: TimePeriod,
    booksState: BooksState,
    userProfileState: UserProfileState,
    onOpenBookDetails: (String) -> Unit,
    onOpenBookList: () -> Unit,
    onOpenProfile: () -> Unit
) {
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
            GoalCard(
                modifier = Modifier.padding(horizontal = 16.dp),
                dateRange = "Aug 29 - Sep 23",
                currentBook = "Philosopher's Stone",
                progress = 0.45f
            )
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
