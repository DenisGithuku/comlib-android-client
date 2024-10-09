
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
import com.githukudenis.comlib.core.data.repository.BooksRepository
import com.githukudenis.comlib.core.data.repository.GenresRepository
import com.githukudenis.comlib.core.data.repository.UserPrefsRepository
import com.githukudenis.comlib.core.model.genre.Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
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
                    prefs.readBooks to prefs.bookmarkedBooks
                }
            ) { bookId, (read, bookmarked) ->
                when (val result = booksRepository.getBookById(bookId)) {
                    is ResponseResult.Failure -> {
                        BookDetailUiState.Error(result.error.message)
                    }
                    is ResponseResult.Success -> {
                        val bookUiModel =
                            result.data.data.book.toBookUiModel(
                                isRead = read.contains(bookId),
                                isFavourite = bookmarked.contains(bookId),
                                getGenre = { genreId ->
                                    fetchGenreById(genreId) ?: Genre(_id = genreId, id = genreId, name = "Unknown")
                                }
                            )
                        BookDetailUiState.Success(bookUiModel = bookUiModel)
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
}
