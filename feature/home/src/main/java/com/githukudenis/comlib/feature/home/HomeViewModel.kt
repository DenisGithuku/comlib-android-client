
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

import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.FetchItemState
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.StatefulViewModel
import com.githukudenis.comlib.core.model.user.User
import com.githukudenis.comlib.data.repository.BookMilestoneRepository
import com.githukudenis.comlib.data.repository.BooksRepository
import com.githukudenis.comlib.data.repository.UserPrefsRepository
import com.githukudenis.comlib.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject

enum class TimePeriod {
    MORNING, AFTERNOON, EVENING
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
) : StatefulViewModel<HomeScreenState>(HomeScreenState()) {

    init {
        getTimePeriod()
        getStreakState()
        getBookmarkedBooks()
        getReadBooks()
        getUserDetails()
        getAvailableBooks()
    }

    private fun getReadBooks() {
        viewModelScope.launch {
            userPrefsRepository.userPrefs.mapLatest { it.readBooks }.collectLatest { update { copy(reads = it.toList()) } }
        }
    }

    private fun getAvailableBooks() {
        update { copy(availableState = FetchItemState.Loading) }
        viewModelScope.launch {
            when(val result = booksRepository.getAllBooks()) {
                is ResponseResult.Failure -> {
                    update { copy(availableState = FetchItemState.Error(message = result.error.message)) }
                }
                is ResponseResult.Success -> {
                    update { copy(availableState = FetchItemState.Success(
                        result.data.data.books.map {
                            BookUiModel(book = it, isFavourite = it.id in state.value.bookmarks)
                        }
                    )) }
                }
            }
        }
    }

    private suspend fun getUserProfile(userId: String) {
        when(val profile = userRepository.getUserById(userId)) {
            is ResponseResult.Failure -> {
                update { copy(user = FetchItemState.Error(message = profile.error.message)) }
            }
            is ResponseResult.Success -> {
                update { copy(user = FetchItemState.Success(data = profile.data.data.user)) }
            }
        }
    }

    fun onClickRetryGetReads() {
        getReadBooks()
    }

    fun onRefreshAvailableBooks() {
        getAvailableBooks()
    }

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

            update { copy(bookmarks = updatedBookMarkSet.toList()) }
            onRefreshAvailableBooks()
        }
    }

    private fun getStreakState() {
        viewModelScope.launch {
            bookMilestoneRepository.bookMilestone
                .collectLatest {
                update { copy(streakState = StreakState(bookMilestone = it)) }
            }
        }
    }

    private fun getUserDetails() {
        viewModelScope.launch {
            userPrefsRepository.userPrefs
                .collectLatest { prefs -> prefs.userId?.let { getUserProfile(it) } }
        }
    }

    private fun getBookmarkedBooks() {
        viewModelScope.launch {
            val bookmarks = userPrefsRepository.userPrefs.mapLatest { it.bookmarkedBooks }.first()
            update { copy(bookmarks = bookmarks.toList()) }
        }
    }

    private fun getTimePeriod() {
        val currHour = Instant.now().atZone(ZoneId.systemDefault()).hour
        val time =
            if (currHour < 12) {
                TimePeriod.MORNING
            } else if (currHour < 16) {
                TimePeriod.AFTERNOON
            } else {
                TimePeriod.EVENING
            }
        update { copy(timePeriod = time) }
    }
}
