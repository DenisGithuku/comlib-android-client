
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class TimePeriod {
    MORNING,
    AFTERNOON,
    EVENING
}

data class HomeScreenState(
    val pagerState: Triple<PaginationState, Int, Int> = Triple(PaginationState.NotLoading, 1, 10),
    val user: FetchItemState<User?> = FetchItemState.Loading,
    val streakState: StreakState = StreakState(),
    val availableState: FetchItemState<List<BookUiModel>> = FetchItemState.Loading,
    val timePeriod: TimePeriod = TimePeriod.MORNING
)

enum class PaginationState {
    Paginating,
    Exhausted,
    NotLoading
}

@HiltViewModel
class HomeViewModel
@Inject
constructor(
    private val userPrefsRepository: UserPrefsRepository,
    private val booksRepository: BooksRepository,
    private val userRepository: UserRepository,
    private val bookMilestoneRepository: BookMilestoneRepository
) : ViewModel() {

    private val _pagingData: MutableStateFlow<Triple<PaginationState, Int, Int>> =
        MutableStateFlow(Triple(PaginationState.NotLoading, 1, 10))

    private val _booksCache: MutableList<BookUiModel> = mutableListOf()

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
            }
                ?: FetchItemState.Error(
                    message = "You are not logged in. Please log in to access the application"
                )
        }

    private val _books: Flow<FetchItemState<List<BookUiModel>>> =
        combine(_pagingData, userPrefsRepository.userPrefs) { (_, page, limit), prefs ->
            if (_booksCache.isNotEmpty()) {
                val updatedCache =
                    _booksCache.map { bookUiModel ->
                        bookUiModel.copy(isFavourite = bookUiModel.book._id in prefs.bookmarkedBooks)
                    }
                FetchItemState.Success(updatedCache)
            } else {
                when (val result = booksRepository.getAllBooks(page = page, limit = limit)) {
                    is ResponseResult.Failure -> {
                        FetchItemState.Error(message = result.error.message)
                    }
                    is ResponseResult.Success -> {
                        val books =
                            result.data.data.books.map {
                                BookUiModel(book = it, isFavourite = it._id in prefs.bookmarkedBooks)
                            }

                        // update only the items that changed their favourite status
                        _booksCache.addAll(books)
                        FetchItemState.Success(_booksCache)
                    }
                }
            }
        }

    val state: StateFlow<HomeScreenState> =
        combine(bookMilestoneRepository.bookMilestone, _books, _userProfile, _pagingData) {
                milestone,
                allBooks,
                profile,
                pagingData ->
                HomeScreenState(
                    streakState = StreakState(bookMilestone = milestone),
                    availableState = allBooks,
                    user = profile,
                    timePeriod = _timePeriod,
                    pagerState = pagingData
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

    fun onRefreshPage() {
        _pagingData.value = _pagingData.value.copy(second = _pagingData.value.second + 1)
    }

    override fun onCleared() {
        _booksCache.clear()
        _pagingData.value = Triple(PaginationState.NotLoading, 1, 10)
        _pagingData.value = _pagingData.value.copy(first = PaginationState.NotLoading)
        super.onCleared()
    }
}
