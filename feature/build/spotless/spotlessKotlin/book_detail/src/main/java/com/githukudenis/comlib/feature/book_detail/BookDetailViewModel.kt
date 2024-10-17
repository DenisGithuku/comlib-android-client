
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
package com.githukudenis.comlib.feature.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.MessageType
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.UserMessage
import com.githukudenis.comlib.core.data.repository.BooksRepository
import com.githukudenis.comlib.core.data.repository.GenresRepository
import com.githukudenis.comlib.core.data.repository.UserPrefsRepository
import com.githukudenis.comlib.core.model.genre.Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class BookDetailViewModel
@Inject
constructor(
    private val userPreferencesRepository: UserPrefsRepository,
    private val booksRepository: BooksRepository,
    private val genresRepository: GenresRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _userMessages = MutableStateFlow<List<UserMessage>>(emptyList())

    private val _updating: MutableStateFlow<Boolean> = MutableStateFlow(false)

    private val _bookCache: MutableStateFlow<BookUiModel?> = MutableStateFlow(null)

    private suspend fun fetchGenreById(genreId: String): Genre? {
        return when (val result = genresRepository.getGenreById(genreId)) {
            is ResponseResult.Failure -> {
                null
            }
            is ResponseResult.Success -> {
                result.data.data.genre
            }
        }
    }

    val state: StateFlow<BookDetailUiState> =
        combine(
                savedStateHandle.getStateFlow(key = "bookId", initialValue = ""),
                userPreferencesRepository.userPrefs.mapLatest { prefs ->
                    Triple(prefs.reservedBooks, prefs.readBooks, prefs.bookmarkedBooks)
                },
                _userMessages,
                _updating
            ) { bookId, (reserved, read, bookmarked), userMessages, updating ->
                if (_bookCache.value != null) {
                    BookDetailUiState.Success(
                        bookUiModel =
                            _bookCache.value!!.copy(
                                isRead = bookId in read,
                                isFavourite = bookId in bookmarked,
                                isReserved = bookId in reserved
                            ),
                        userMessages = userMessages,
                        updating = updating
                    )
                } else {
                    when (val result = booksRepository.getBookById(bookId)) {
                        is ResponseResult.Failure -> {
                            BookDetailUiState.Error(result.error.message)
                        }
                        is ResponseResult.Success -> {
                            val bookUiModel =
                                result.data.data.book.toBookUiModel(
                                    isRead = bookId in read,
                                    isFavourite = bookId in bookmarked,
                                    isReserved = bookId in reserved,
                                    getGenre = { genreId ->
                                        fetchGenreById(genreId) ?: Genre(_id = genreId, id = genreId, name = "Unknown")
                                    }
                                )
                            _bookCache.update { bookUiModel }
                            BookDetailUiState.Success(
                                bookUiModel = _bookCache.value ?: bookUiModel,
                                userMessages = userMessages,
                                updating
                            )
                        }
                    }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = BookDetailUiState.Loading
            )

    fun toggleBookmark(bookId: String) {
        viewModelScope.launch {
            val bookMarks = userPreferencesRepository.userPrefs.mapLatest { it.bookmarkedBooks }.first()
            val updatedBookmarkSet =
                if (bookMarks.contains(bookId)) {
                    bookMarks.minus(bookId)
                } else {
                    bookMarks.plus(bookId)
                }
            userPreferencesRepository.setBookMarks(updatedBookmarkSet)
        }
    }

    fun onReserveBook(bookId: String) {
        viewModelScope.launch {
            _updating.update { true }
            val (userId, reservedBooks) =
                userPreferencesRepository.userPrefs.mapLatest { Pair(it.userId, it.reservedBooks) }.first()
            if (userId.isNullOrEmpty()) {
                _updating.update { false }
                return@launch
            }

            when (val reserveResult = booksRepository.reserveBook(bookId, userId)) {
                is ResponseResult.Failure -> {
                    _updating.update { false }
                    onShowUserMessage(
                        UserMessage(
                            id = 1,
                            message = reserveResult.error.message,
                            messageType = MessageType.ERROR
                        )
                    )
                }
                is ResponseResult.Success -> {
                    userPreferencesRepository.setReservedBooks(reservedBooks.plus(bookId))
                    _updating.update { false }
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
    }

    fun onUnReserveBook(bookId: String) {
        viewModelScope.launch {
            _updating.update { true }
            val (userId, reservedBooks) =
                userPreferencesRepository.userPrefs.mapLatest { Pair(it.userId, it.reservedBooks) }.first()
            if (userId.isNullOrEmpty()) {
                _updating.update { false }
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
                    _updating.update { false }
                    onShowUserMessage(
                        UserMessage(
                            id = 1,
                            message = reserveResult.error.message,
                            messageType = MessageType.ERROR
                        )
                    )
                }
                is ResponseResult.Success -> {
                    userPreferencesRepository.setReservedBooks(reservedBooks.minus(bookId))
                    _updating.update { false }
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
    }

    fun onShowUserMessage(message: UserMessage) {
        _userMessages.update { it.plus(message) }
    }

    fun onUserMessageShown(messageId: Int) {
        _userMessages.update { it.filterNot { message -> message.id == messageId } }
    }
}
