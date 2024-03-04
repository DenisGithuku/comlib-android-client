package com.githukudenis.comlib.feature.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.githukudenis.comlib.core.common.FetchItemState
import com.githukudenis.comlib.core.designsystem.ui.components.SectionSeparator
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibOutlinedButton
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens
import com.githukudenis.comlib.feature.home.components.BookCard
import com.githukudenis.comlib.feature.home.components.EmptyDataCard
import com.githukudenis.comlib.feature.home.components.ErrorCard
import com.githukudenis.comlib.feature.home.components.GoalCard
import com.githukudenis.comlib.feature.home.components.HomeHeader
import com.githukudenis.comlib.feature.home.components.LoadingBookCard

/**
 * project : ComLib
 * date    : Friday 23/02/2024
 * time    : 12:45 pm
 * user    : mambo
 * email   : mambobryan@gmail.com
 */

@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    onOpenBookDetails: (String) -> Unit,
    onOpenAllBooks: () -> Unit,
    onOpenProfile: () -> Unit,
) {
    val state by viewModel.state.collectAsState()
    HomeRouteContent(
        state = state,
        onOpenProfile = onOpenProfile,
        onClickRetryGetReads = viewModel::onClickRetryGetReads,
        onClickRetryGetAvailableBooks = viewModel::onClickRetryGetAvailableBooks,
        onOpenAllBooks = onOpenAllBooks,
        onOpenBookDetails = onOpenBookDetails
    )
}

@Composable
fun HomeRouteContent(
    state: HomeScreenState,
    onClickRetryGetReads: () -> Unit,
    onOpenProfile: () -> Unit,
    onOpenAllBooks: () -> Unit,
    onOpenBookDetails: (String) -> Unit,
    onClickRetryGetAvailableBooks: () -> Unit,
) {
    Scaffold { values ->
        LazyColumn(
            modifier = Modifier
                .padding(values)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(LocalDimens.current.extraLarge)
        ) {
            item {
                HomeHeader(modifier = Modifier.padding(horizontal = LocalDimens.current.extraLarge), title = {
                    Text(
                        text = buildString {
                            append("Good")
                            append(" ")
                            append("Afternoon")
                            append(" ")
                            append("Stranger")
                        }, style = MaterialTheme.typography.titleMedium
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
                            .clickable(
                                       onClick = onOpenProfile
                            ),
                        model = "https://comlib-api.onrender.com/img/users/default_img.jpg",
                        contentDescription = "User profile"
                    )
                })
            }
            item {
                GoalCard(modifier = Modifier.padding(horizontal = LocalDimens.current.extraLarge),
                    hasStreak = state.streakState.bookMilestone != null,
                    onSetStreak = { },
                    onOpenStreakDetails = {})
            }
            item {
                SectionSeparator(
                    modifier = Modifier.padding(horizontal = LocalDimens.current.extraLarge),
                    title = stringResource(id = R.string.available_books_separator),
                    onViewAll = onOpenAllBooks
                )
            }
            item {
                when (state.availableState) {
                    is FetchItemState.Error -> {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            ErrorCard(content = state.availableState.message)
                            CLibOutlinedButton(
                                modifier = Modifier.padding(top = LocalDimens.current.medium),
                                onClick = onClickRetryGetAvailableBooks
                            ) {
                                Text(text = stringResource(id = R.string.retry))
                            }
                        }
                    }

                    FetchItemState.Loading -> {
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = LocalDimens.current.extraLarge),
                            horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.large)
                        ) {
                            items(4) {
                                LoadingBookCard()
                            }
                        }
                    }

                    is FetchItemState.Success -> {
                        val list = state.availableState.data
                        if (list.isNotEmpty()) {
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = LocalDimens.current.extraLarge),
                                horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.medium)
                            ) {
                                items(list, key = { book -> book._id }) { book ->
                                    BookCard(book = book, onClick = onOpenBookDetails)
                                }
                            }
                        } else {
                            EmptyDataCard(content = "books")
                        }
                    }
                }
            }
        }
    }
}