
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
import com.githukudenis.comlib.core.common.MessageType
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.UserMessage
import com.githukudenis.comlib.core.data.repository.BookMilestoneRepository
import com.githukudenis.comlib.core.data.repository.BooksRepository
import com.githukudenis.comlib.core.data.repository.UserPrefsRepository
import com.githukudenis.comlib.core.model.UserProfileData
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Instant
import java.time.ZoneId
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class TimePeriod {
    MORNING,
    AFTERNOON,
    EVENING
}

data class HomeScreenState(
    val pagerState: Triple<PaginationState, Int, Int> = Triple(PaginationState.NotLoading, 1, 10),
    val userProfileData: UserProfileData = UserProfileData(),
    val streakState: StreakState = StreakState(),
    val availableState: FetchItemState<List<BookUiModel>> = FetchItemState.Loading,
    val timePeriod: TimePeriod = TimePeriod.MORNING,
    val userMessages: List<UserMessage> = emptyList(),
    val isReserving: Boolean = false
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
    private val bookMilestoneRepository: BookMilestoneRepository
) : ViewModel() {

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

    private val _state: MutableStateFlow<HomeScreenState> = MutableStateFlow(HomeScreenState())
    val state: StateFlow<HomeScreenState> = _state.asStateFlow()

    init {
        getUserData()
        fetchMilestone()
        fetchBooks()
    }

    private fun getUserData() {
        viewModelScope.launch {
            userPrefsRepository.userPrefs.collectLatest {
                _state.update { state ->
                    state.copy(timePeriod = _timePeriod, userProfileData = it.userProfileData)
                }
            }
        }
    }

    private fun fetchMilestone() {
        viewModelScope.launch {
            bookMilestoneRepository.bookMilestone.collectLatest {
                _state.update { state -> state.copy(streakState = StreakState(bookMilestone = it)) }
            }
        }
    }

    private fun fetchBooks() {
        viewModelScope.launch {
            _state.update { it.copy(availableState = FetchItemState.Loading) }
            userPrefsRepository.userPrefs.collectLatest { prefs ->
                if (_booksCache.isNotEmpty()) {
                    val updatedCache =
                        _booksCache.map { bookUiModel ->
                            bookUiModel.copy(
                                isFavourite = bookUiModel.book._id in prefs.bookmarkedBooks,
                                isReserved = bookUiModel.book._id in prefs.reservedBooks
                            )
                        }
                    _state.update { it.copy(availableState = FetchItemState.Success(updatedCache)) }
                } else {
                    when (
                        val result =
                            booksRepository.getAllBooks(
                                page = _state.value.pagerState.second,
                                limit = _state.value.pagerState.third
                            )
                    ) {
                        is ResponseResult.Failure -> {
                            _state.update {
                                it.copy(availableState = FetchItemState.Error(message = result.error.message))
                            }
                        }
                        is ResponseResult.Success -> {
                            val books =
                                result.data.data.books.map {
                                    BookUiModel(
                                        book = it,
                                        isFavourite = it._id in prefs.bookmarkedBooks,
                                        isReserved = it._id in prefs.reservedBooks
                                    )
                                }

                            // update cache to prevent unnecessary re-fetching
                            _booksCache.addAll(books)
                            _state.update { it.copy(availableState = FetchItemState.Success(_booksCache)) }
                        }
                    }
                }
            }
        }
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
            fetchBooks()
        }
    }

    fun onRefreshPage() {
        _state.update { it.copy(pagerState = it.pagerState.copy(second = it.pagerState.second + 1)) }
        fetchBooks()
    }

    fun onReserveBook(bookId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isReserving = true) }
            val (userId, reservedBooks) =
                userPrefsRepository.userPrefs.mapLatest { Pair(it.userId, it.reservedBooks) }.first()
            if (userId.isNullOrEmpty()) {
                _state.update { it.copy(isReserving = false) }
                onShowUserMessage(
                    UserMessage(
                        id = 1,
                        message = "You must be logged in to reserve a book",
                        messageType = MessageType.ERROR
                    )
                )
                return@launch
            }

            when (val reserveResult = booksRepository.reserveBook(bookId, userId)) {
                is ResponseResult.Failure -> {
                    _state.update { it.copy(isReserving = false) }
                    onShowUserMessage(
                        UserMessage(
                            id = 1,
                            message = reserveResult.error.message,
                            messageType = MessageType.ERROR
                        )
                    )
                }
                is ResponseResult.Success -> {
                    userPrefsRepository.setReservedBooks(reservedBooks.plus(bookId))
                    _state.update { it.copy(isReserving = false) }
                    onShowUserMessage(
                        UserMessage(
                            id = 1,
                            message = reserveResult.data.message,
                            messageType = MessageType.INFO
                        )
                    )
                }
            }
        }
        onRefreshAvailableBooks()
    }

    fun onUnReserveBook(bookId: String) {
        viewModelScope.launch {
            _state.update { it.copy(isReserving = true) }
            val (userId, reservedBooks) =
                userPrefsRepository.userPrefs.mapLatest { Pair(it.userId, it.reservedBooks) }.first()
            if (userId.isNullOrEmpty()) {
                _state.update { it.copy(isReserving = false) }
                onShowUserMessage(
                    UserMessage(
                        id = 1,
                        message = "You must be logged in to reserve a book",
                        messageType = MessageType.ERROR
                    )
                )
                return@launch
            }

            when (val reserveResult = booksRepository.unReserveBook(bookId, userId)) {
                is ResponseResult.Failure -> {
                    _state.update { it.copy(isReserving = false) }
                    onShowUserMessage(
                        UserMessage(
                            id = 1,
                            message = reserveResult.error.message,
                            messageType = MessageType.ERROR
                        )
                    )
                }
                is ResponseResult.Success -> {
                    userPrefsRepository.setReservedBooks(reservedBooks.minus(bookId))
                    _state.update { it.copy(isReserving = false) }
                    onShowUserMessage(
                        UserMessage(
                            id = 1,
                            message = reserveResult.data.message,
                            messageType = MessageType.INFO
                        )
                    )
                }
            }
        }
        onRefreshAvailableBooks()
    }

    fun onRefreshAvailableBooks() {
        fetchBooks()
    }

    fun onShowUserMessage(message: UserMessage) {
        _state.update { it.copy(userMessages = it.userMessages + message) }
    }

    fun onUserMessageShown(messageId: Int) {
        _state.update { state ->
            state.copy(userMessages = state.userMessages.filterNot { it.id == messageId })
        }
    }

    override fun onCleared() {
        _booksCache.clear()
        super.onCleared()
    }
}
