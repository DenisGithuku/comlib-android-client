
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
import com.githukudenis.comlib.core.common.DataResult
import com.githukudenis.comlib.core.domain.usecases.GetBookDetailsUseCase
import com.githukudenis.comlib.core.domain.usecases.GetBookmarkedBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetGenreByIdUseCase
import com.githukudenis.comlib.core.domain.usecases.GetReadBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.ToggleBookMarkUseCase
import com.githukudenis.comlib.core.model.genre.Genre
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
    private val getBookmarkedBooksUseCase: GetBookmarkedBooksUseCase,
    private val getBookDetailsUseCase: GetBookDetailsUseCase,
    private val getReadBooksUseCase: GetReadBooksUseCase,
    private val getGenreByIdUseCase: GetGenreByIdUseCase,
    private val toggleBookMarkUseCase: ToggleBookMarkUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state: MutableStateFlow<BookDetailUiState> =
        MutableStateFlow(BookDetailUiState.Loading)
    val state: StateFlow<BookDetailUiState> = _state.asStateFlow()

    private val bookId: String? = savedStateHandle.get<String>("bookId")

    val isFavourite: StateFlow<Boolean> =
        getBookmarkedBooksUseCase()
            .distinctUntilChanged()
            .mapLatest { bookMarks -> bookMarks.contains(bookId) }
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
            getBookDetailsUseCase.invoke(bookId).collect { result ->
                when (result) {
                    DataResult.Empty -> Unit
                    is DataResult.Error -> _state.update { BookDetailUiState.Error(result.message) }
                    is DataResult.Loading -> {
                        _state.update { BookDetailUiState.Loading }
                    }
                    is DataResult.Success -> {
                        val isRead = getReadBooksUseCase().first().contains(result.data.id)
                        _state.update {
                            val bookUiModel =
                                result.data.toBookUiModel(
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
    }

    fun onRetry() {
        if (bookId != null) {
            getBook(bookId)
        }
    }

    private suspend fun fetchGenreById(genreId: String): Genre? {
        return getGenreByIdUseCase(genreId)
    }

    fun toggleBookmark(bookId: String) {
        viewModelScope.launch {
            val bookMarks = getBookmarkedBooksUseCase().first()
            val updatedBookmarkSet =
                if (bookMarks.contains(bookId)) {
                    bookMarks.minus(bookId)
                } else {
                    bookMarks.plus(bookId)
                }
            toggleBookMarkUseCase(updatedBookmarkSet)
        }
    }
}
