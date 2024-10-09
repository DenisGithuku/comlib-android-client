
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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.FetchItemState
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.data.repository.BookMilestoneRepository
import com.githukudenis.comlib.core.data.repository.BooksRepository
import com.githukudenis.comlib.core.data.repository.UserPrefsRepository
import com.githukudenis.comlib.core.data.repository.UserRepository
import com.githukudenis.comlib.core.model.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class TimePeriod {
    MORNING,
    AFTERNOON,
    EVENING
}

data class HomeScreenState(
    val user: FetchItemState<User?> = FetchItemState.Loading,
    val reads: List<String> = emptyList(),
    val bookmarks: List<String> = emptyList(),
    val streakState: StreakState = StreakState(),
    val availableState: FetchItemState<List<BookUiModel>> = FetchItemState.Loading,
    val timePeriod: TimePeriod = TimePeriod.MORNING
)

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val userPrefsRepository: UserPrefsRepository,
    private val booksRepository: BooksRepository,
    private val userRepository: UserRepository,
    private val bookMilestoneRepository: BookMilestoneRepository
) : ViewModel() {

    private val _timePeriod: TimePeriod
        get() {
            val currHour = Instant.now().atZone(ZoneId.systemDefault()).hour
            val time =
                if (currHour < 12) {
                    TimePeriod.MORNING
                } else if (currHour < 16) {
                    TimePeriod.AFTERNOON
                } else {
                    TimePeriod.EVENING
                }
            return time
        }

    private val _userProfile: Flow<FetchItemState<User?>> =
        userPrefsRepository.userPrefs.mapLatest { prefs ->
            prefs.userId?.let { id ->
                when (val profile = userRepository.getUserById(id)) {
                    is ResponseResult.Failure -> {
                        FetchItemState.Error(message = profile.error.message)
                    }
                    is ResponseResult.Success -> {
                        FetchItemState.Success(data = profile.data.data.user)
                    }
                }
            } ?: FetchItemState.Error(message = "User not logged in")
        }

    private val _books: Flow<FetchItemState<List<BookUiModel>>> = flow {
        when (val result = booksRepository.getAllBooks()) {
            is ResponseResult.Failure -> {
                emit(FetchItemState.Error(message = result.error.message))
            }
            is ResponseResult.Success -> {
                emit(FetchItemState.Success(result.data.data.books.map { BookUiModel(book = it) }))
            }
        }
    }

    val state: StateFlow<HomeScreenState> =
        combine(
                userPrefsRepository.userPrefs.mapLatest { prefs ->
                    prefs.bookmarkedBooks to prefs.readBooks
                },
                bookMilestoneRepository.bookMilestone,
                _books,
                _userProfile
            ) { (bookmarks, read), milestone, allBooks, profile ->
                HomeScreenState(
                    bookmarks = bookmarks.toList(),
                    reads = read.toList(),
                    streakState = StreakState(bookMilestone = milestone),
                    availableState = allBooks,
                    user = profile,
                    timePeriod = _timePeriod
                )
            }
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), HomeScreenState())

    fun onToggleFavourite(id: String) {
        viewModelScope.launch {
            val bookMarks = userPrefsRepository.userPrefs.mapLatest { it.bookmarkedBooks }.first()
            val updatedBookMarkSet =
                if (id in bookMarks) {
                    bookMarks.minus(id)
                } else {
                    bookMarks.plus(id)
                }
            userPrefsRepository.setBookMarks(updatedBookMarkSet)
        }
    }
}
