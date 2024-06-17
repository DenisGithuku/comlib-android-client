
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
import com.githukudenis.comlib.core.common.DataResult
import com.githukudenis.comlib.core.common.FetchItemState
import com.githukudenis.comlib.core.common.StatefulViewModel
import com.githukudenis.comlib.core.domain.usecases.GetAllBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetBookmarkedBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetReadBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetStreakUseCase
import com.githukudenis.comlib.core.domain.usecases.GetUserPrefsUseCase
import com.githukudenis.comlib.core.domain.usecases.GetUserProfileUseCase
import com.githukudenis.comlib.core.domain.usecases.TimePeriod
import com.githukudenis.comlib.core.domain.usecases.ToggleBookMarkUseCase
import com.githukudenis.comlib.core.model.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

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
    private val getReadBooksUseCase: GetReadBooksUseCase,
    private val getAllBooksUseCase: GetAllBooksUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getBookmarkedBooksUseCase: GetBookmarkedBooksUseCase,
    private val toggleBookMarkUseCase: ToggleBookMarkUseCase,
    private val getStreakUseCase: GetStreakUseCase,
    private val getUserPrefsUseCase: GetUserPrefsUseCase
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
            getReadBooksUseCase().collectLatest { update { copy(reads = it.toList()) } }
        }
    }

    private fun getAvailableBooks() {
        viewModelScope.launch {
            getAllBooksUseCase().collectLatest { result ->
                val value =
                    when (result) {
                        DataResult.Empty -> FetchItemState.Success(emptyList())
                        is DataResult.Error -> FetchItemState.Error(message = result.message)
                        is DataResult.Loading -> FetchItemState.Loading
                        is DataResult.Success ->
                            FetchItemState.Success(
                                result.data.map {
                                    BookUiModel(book = it, isFavourite = it.id in state.value.bookmarks)
                                }
                            )
                    }
                update { copy(availableState = value) }
            }
        }
    }

    private suspend fun getUserProfile(userId: String) {
        val profile = getUserProfileUseCase(userId)
        if (profile == null) {
            update { copy(user = FetchItemState.Error(message = "Could not fetch profile")) }
        } else {
            update { copy(FetchItemState.Success(data = profile)) }
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
            val bookMarks = getBookmarkedBooksUseCase().first()
            val updatedBookMarkSet =
                if (id in bookMarks) {
                    bookMarks.minus(id)
                } else {
                    bookMarks.plus(id)
                }
            toggleBookMarkUseCase(updatedBookMarkSet)

            update { copy(bookmarks = updatedBookMarkSet.toList()) }
            onRefreshAvailableBooks()
        }
    }

    private fun getStreakState() {
        viewModelScope.launch {
            getStreakUseCase().collectLatest {
                update { copy(streakState = StreakState(bookMilestone = it)) }
            }
        }
    }

    private fun getUserDetails() {
        viewModelScope.launch {
            getUserPrefsUseCase().collectLatest { prefs ->
                requireNotNull(prefs.authId).also { getUserProfile(it) }
            }
        }
    }

    private fun getBookmarkedBooks() {
        viewModelScope.launch {
            val bookmarks = getBookmarkedBooksUseCase().first()
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
