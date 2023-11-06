package com.githukudenis.comlib.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.githukudenis.comlib.core.designsystem.ui.components.SectionSeparator
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibButton
import com.githukudenis.comlib.core.domain.usecases.TimePeriod
import com.githukudenis.comlib.core.model.user.User
import com.githukudenis.comlib.feature.home.components.BookCard
import com.githukudenis.comlib.feature.home.components.GoalCard
import com.githukudenis.comlib.feature.home.components.HomeHeader

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onRefresh = { viewModel.onEvent(HomeUiEvent.Refresh) }
    HomeScreen(state = state, onRefresh = onRefresh)
}

@Composable
private fun HomeScreen(
    state: HomeUiState, onRefresh: () -> Unit
) {
    when (state) {
        is HomeUiState.Error -> ErrorScreen(error = state.message, onRetry = onRefresh)
        HomeUiState.Loading -> LoadingScreen()
        is HomeUiState.Success -> LoadedScreen(
            booksState = state.booksState, profile = state.user, timePeriod = state.timePeriod
        )
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LoadedScreen(
    timePeriod: TimePeriod, booksState: BooksState, profile: User?
) {
    LazyColumn(
        modifier = Modifier
            .consumeWindowInsets(
                paddingValues = PaddingValues(
                    top = WindowInsets.systemBars
                        .asPaddingValues()
                        .calculateTopPadding(),
                    bottom = WindowInsets.systemBars
                        .asPaddingValues()
                        .calculateBottomPadding() + WindowInsets.navigationBars
                        .asPaddingValues()
                        .calculateBottomPadding()
                )
            )
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        item {
            HomeHeader(modifier = Modifier.padding(16.dp), title = {
                Text(
                    text = stringResource(id = R.string.home_header_title,
                        timePeriod.name.lowercase().replaceFirstChar { char -> char.uppercase() },
                        (profile?.firstname
                            ?: "stranger").replaceFirstChar { char -> char.uppercase() }),
                    style = MaterialTheme.typography.titleMedium
                )
            }, subtitle = {
                // TODO("Fetch name and time")
                Text(
                    text = stringResource(id = R.string.home_header_subtitle),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                )
            }, profileImage = {
                "https://comlib-api.onrender.com/img/users/${profile?.image}"
            })
        }
        item {
            GoalCard(
                modifier = Modifier.padding(16.dp),
                dateRange = "Aug 29 - Sep 23",
                currentBook = "Philosopher's Stone",
                progress = 0.45f
            )
        }
        item {
            SectionSeparator(modifier = Modifier.padding(16.dp),
                title = stringResource(id = R.string.already_read_separator),
                count = when (booksState) {
                    is BooksState.Error -> "0"
                    BooksState.Loading -> "0"
                    is BooksState.Success -> "${booksState.readBooks.size - 1}"
                },
                onViewAll = {})
        }

        item {
            when (booksState) {
                is BooksState.Error -> Unit
                BooksState.Loading -> CircularProgressIndicator()
                is BooksState.Success -> {
                    LazyRow(
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(booksState.readBooks, key = { book -> book._id }) { book ->
                            BookCard(book = book)
                        }
                    }
                }
            }
        }
        item {
            SectionSeparator(modifier = Modifier.padding(16.dp),
                title = stringResource(id = R.string.available_books_separator),
                count = when (booksState) {
                    is BooksState.Error -> "0"
                    BooksState.Loading -> "0"
                    is BooksState.Success -> "${booksState.available.size - 1}"
                },
                onViewAll = {})
        }

        item {
            when (booksState) {
                is BooksState.Error -> Unit
                BooksState.Loading -> CircularProgressIndicator()
                is BooksState.Success -> {
                    LazyRow(
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(booksState.readBooks, key = { book -> book._id }) { book ->
                            BookCard(book = book)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(error: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.CenterStart
    ) {
        Column(modifier = Modifier.padding(horizontal = 24.dp)) {
            Text(
                text = error, style = MaterialTheme.typography.titleMedium
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
