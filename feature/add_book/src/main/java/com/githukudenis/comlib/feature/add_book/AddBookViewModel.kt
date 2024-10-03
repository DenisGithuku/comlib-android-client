
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
package com.githukudenis.comlib.feature.add_book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.core.model.book.toBookDTO
import com.githukudenis.comlib.data.repository.BooksRepository
import com.githukudenis.comlib.data.repository.GenresRepository
import com.githukudenis.comlib.data.repository.UserPrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel
@Inject
constructor(
    private val booksRepository: BooksRepository,
    private val userPrefsRepository: UserPrefsRepository,
    private val genresRepository: GenresRepository
) : ViewModel() {

    var state = MutableStateFlow(AddBookUiState())
        private set

    init {
        loadGenres()
    }

    private fun loadGenres() {
        viewModelScope.launch {
            state.update { it.copy(genreState = GenreUiState.Loading) }
            val result = genresRepository.getGenres()
            when (result) {
                is ResponseResult.Failure -> {
                    state.update { it.copy(genreState = GenreUiState.Error(result.error.message)) }
                }
                is ResponseResult.Success -> {
                    val genres = result.data
                        .data
                        .genres
                    state.update { it.copy(genreState = GenreUiState.Success(genres)) }
                }
            }
        }
    }

    fun onEvent(event: AddBookUiEvent) {
        when (event) {
            is AddBookUiEvent.OnAuthorChange -> {
                state.update { it.copy(authors = event.newValue) }
            }
            is AddBookUiEvent.OnDescriptionChange -> {
                state.update { it.copy(description = event.newValue) }
            }
            is AddBookUiEvent.OnEditionChange -> {
                state.update { it.copy(edition = event.newValue) }
            }
            is AddBookUiEvent.OnGenreChange -> {
                state.update { it.copy(selectedGenre = event.newValue) }
            }
            is AddBookUiEvent.OnTitleChange -> {
                state.update { it.copy(title = event.newValue) }
            }
            is AddBookUiEvent.OnPageChange -> {
                state.update { it.copy(pages = event.newValue) }
            }
            is AddBookUiEvent.OnChangePhoto -> {
                state.update { it.copy(photoUri = event.uri) }
            }
            is AddBookUiEvent.ShowMessage -> {
                state.update { it.copy(message = event.message) }
            }
            AddBookUiEvent.OnSave -> {
                if (state.value.uiIsValid) {
                    onSaveBook()
                } else {
                    state.update { it.copy(message = "Please check details!") }
                }
            }
            AddBookUiEvent.DismissMessage -> {
                state.update { it.copy(message = "") }
            }
            AddBookUiEvent.OnRetryLoadGenres -> {
                loadGenres()
            }
        }
    }

    private fun onSaveBook() {
        viewModelScope.launch {
            state.update { it.copy(isLoading = true) }

            val book =
                Book(
                    title = state.value.title,
                    authors = state.value.authors.split(','),
                    pages = state.value.pages.toInt(),
                    genre_ids = listOf(state.value.selectedGenre.id),
                    owner = userPrefsRepository.userPrefs.first().userId ?: return@launch,
                    edition = state.value.edition,
                    description = state.value.description
                )
            state.value.photoUri?.let { uri ->
                booksRepository.addNewBook(imageUri = uri, book = book.toBookDTO())
            }
            state.update { it.copy(isLoading = false, isSuccess = true) }
        }
    }

    override fun onCleared() {
        state.update { AddBookUiState() }
    }
}
