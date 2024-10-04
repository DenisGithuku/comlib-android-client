
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
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.model.genre.Genre
import com.githukudenis.comlib.data.repository.BooksRepository
import com.githukudenis.comlib.data.repository.GenresRepository
import com.githukudenis.comlib.data.repository.UserPrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
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

    private val _state: MutableStateFlow<BookDetailUiState> =
        MutableStateFlow(BookDetailUiState.Loading)
    val state: StateFlow<BookDetailUiState> = _state.asStateFlow()

    private val bookId: String? = savedStateHandle.get<String>("bookId")

    val isFavourite: StateFlow<Boolean> =
        userPreferencesRepository.userPrefs
            .distinctUntilChanged()
            .mapLatest { userPrefs -> userPrefs.bookmarkedBooks.contains(bookId) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = false
            )

    init {
        savedStateHandle.get<String>("bookId")?.let { bookId -> getBook(bookId) }
    }

    private fun getBook(bookId: String) {
        viewModelScope.launch {
            when (val result = booksRepository.getBookById(bookId)) {
                is ResponseResult.Failure -> {
                    _state.update { BookDetailUiState.Error(result.error.message) }
                }
                is ResponseResult.Success -> {
                    val isRead =
                        userPreferencesRepository.userPrefs.first().readBooks.contains(result.data.data.book.id)
                    _state.update {
                        val bookUiModel =
                            result.data.data.book.toBookUiModel(
                                isRead = isRead,
                                getGenre = { genreId ->
                                    fetchGenreById(genreId) ?: Genre(_id = genreId, id = genreId, name = "Unknown")
                                }
                            )
                        BookDetailUiState.Success(bookUiModel = bookUiModel)
                    }
                }
            }
        }
    }

    fun onRetry() {
        if (bookId != null) {
            getBook(bookId)
        }
    }

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
}
