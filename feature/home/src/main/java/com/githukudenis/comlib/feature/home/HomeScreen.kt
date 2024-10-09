
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
package com.githukudenis.comlib.feature.home

import android.widget.Toast
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.githukudenis.comlib.core.common.FetchItemState
import com.githukudenis.comlib.core.common.capitalize
import com.githukudenis.comlib.core.designsystem.ui.components.SectionSeparator
import com.githukudenis.comlib.core.designsystem.ui.components.buttons.CLibOutlinedButton
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens
import com.githukudenis.comlib.feature.home.components.BookCard
import com.githukudenis.comlib.feature.home.components.EmptyDataCard
import com.githukudenis.comlib.feature.home.components.ErrorCard
import com.githukudenis.comlib.feature.home.components.GoalCard
import com.githukudenis.comlib.feature.home.components.HomeHeader
import com.githukudenis.comlib.feature.home.components.LoadingBookCard
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn
import kotlinx.datetime.toLocalDateTime

/**
 * project : ComLib date : Friday 23/02/2024 time : 12:45 pm user : mambo email :
 * mambobryan@gmail.com
 */
@Composable
fun HomeRoute(
    viewModel: HomeViewModel = hiltViewModel(),
    onOpenBookDetails: (String) -> Unit,
    onOpenAllBooks: () -> Unit,
    onOpenProfile: () -> Unit,
    onNavigateToStreakDetails: (String?) -> Unit
) {
    val state by viewModel.state.collectAsState()
    HomeRouteContent(
        state = state,
        onOpenProfile = onOpenProfile,
        onClickRetryGetReads = {
            //            viewModel::onClickRetryGetReads
        },
        onClickRetryGetAvailableBooks = {
            //            viewModel::onRefreshAvailableBooks
        },
        onOpenAllBooks = onOpenAllBooks,
        onOpenBookDetails = onOpenBookDetails,
        onToggleFavourite = viewModel::onToggleFavourite,
        onNavigateToStreakDetails = onNavigateToStreakDetails
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeRouteContent(
    state: HomeScreenState,
    onClickRetryGetReads: () -> Unit,
    onOpenProfile: () -> Unit,
    onOpenAllBooks: () -> Unit,
    onOpenBookDetails: (String) -> Unit,
    onClickRetryGetAvailableBooks: () -> Unit,
    onToggleFavourite: (String) -> Unit,
    onNavigateToStreakDetails: (String?) -> Unit
) {
    val context = LocalContext.current

    Scaffold { values ->
        LazyColumn(
            modifier =
                Modifier.padding(values).padding(vertical = LocalDimens.current.extraLarge).fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(LocalDimens.current.extraLarge)
        ) {
            item {
                val username =
                    when (state.user) {
                        is FetchItemState.Error -> "Stranger"
                        FetchItemState.Loading -> "Stranger"
                        is FetchItemState.Success -> state.user.data?.firstname?.capitalize()
                    }

                val time = state.timePeriod.name.lowercase().capitalize()
                HomeHeader(
                    modifier = Modifier.padding(horizontal = LocalDimens.current.extraLarge),
                    title = {
                        Text(
                            text =
                                buildString {
                                    append("Good")
                                    append(" ")
                                    append(time)
                                    append(" ")
                                    append(username)
                                },
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    subtitle = {
                        Text(
                            text = stringResource(id = R.string.home_header_subtitle),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
                        )
                    },
                    profileImage = {
                        AsyncImage(
                            modifier = Modifier.size(32.dp).clip(CircleShape).clickable(onClick = onOpenProfile),
                            contentScale = ContentScale.Crop,
                            model =
                                when (val user = state.user) {
                                    is FetchItemState.Error -> context.getDrawable(R.drawable.placeholder_no_text)
                                    FetchItemState.Loading -> context.getDrawable(R.drawable.placeholder_no_text)
                                    is FetchItemState.Success -> user.data?.image
                                },
                            contentDescription = "User profile"
                        )
                    }
                )
            }
            item {
                val dateRange = buildString {
                    append(state.streakState.bookMilestone?.startDate?.toLocalDate()?.toDayAndMonth())
                    append(" - ")
                    append(state.streakState.bookMilestone?.endDate?.toLocalDate()?.toDayAndMonth())
                }

                val progress =
                    state.streakState.bookMilestone?.run {
                        endDate?.let { startDate?.let { it1 -> calculateProgress(it1, it) } }
                    } ?: 0f
                GoalCard(
                    hasStreak = state.streakState.bookMilestone != null,
                    dateRange = dateRange,
                    progress = progress,
                    bookId = state.streakState.bookMilestone?.bookId,
                    currentBookTitle = state.streakState.bookMilestone?.bookName,
                    onOpenStreakDetails = onNavigateToStreakDetails
                )
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
                            items(4) { LoadingBookCard() }
                        }
                    }
                    is FetchItemState.Success -> {
                        val books =
                            state.availableState.data.map { model ->
                                model.copy(isFavourite = model.isFavourite == model.book.id in state.bookmarks)
                            }
                        if (books.isNotEmpty()) {
                            LazyRow(
                                contentPadding = PaddingValues(horizontal = LocalDimens.current.extraLarge),
                                horizontalArrangement = Arrangement.spacedBy(LocalDimens.current.large)
                            ) {
                                items(books, key = { bookUiModel -> bookUiModel.book._id }) { bookUiModel ->
                                    BookCard(
                                        bookUiModel = bookUiModel,
                                        onClick = onOpenBookDetails,
                                        onReserve = {},
                                        onToggleFavourite = {
                                            onToggleFavourite(bookUiModel.book.id)
                                            Toast.makeText(
                                                    context,
                                                    context.getString(
                                                        if (bookUiModel.isFavourite) {
                                                            R.string.remove_from_favourites
                                                        } else R.string.add_to_favourites
                                                    ),
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                        }
                                    )
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

private fun LocalDate.toMillisLong(): Long {
    return this.atStartOfDayIn(TimeZone.currentSystemDefault()).toEpochMilliseconds()
}

private fun Long.toLocalDate(): LocalDate {
    val instant = Instant.fromEpochMilliseconds(this)
    return instant.toLocalDateTime(TimeZone.currentSystemDefault()).date
}

private fun LocalDate.toDayAndMonth(): String {
    return buildString {
        append(this@toDayAndMonth.dayOfMonth)
        append(" ")
        append(this@toDayAndMonth.month.name.take(3).capitalize())
    }
}

private fun calculateProgress(startDate: Long, endDate: Long): Float {
    val now = Clock.System.now().toEpochMilliseconds().toLocalDate()
    val progress =
        if (startDate.toLocalDate() > now) {
            0f
        } else
            (now.dayOfYear - startDate.toLocalDate().dayOfYear).toFloat() /
                (endDate.toLocalDate().dayOfYear - startDate.toLocalDate().dayOfYear).toFloat()
    return progress
}
