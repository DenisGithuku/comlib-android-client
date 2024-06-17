
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
import com.githukudenis.comlib.data.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class AddBookViewModel @Inject constructor(private val booksRepository: BooksRepository) :
    ViewModel() {
    var state = MutableStateFlow(AddBookUiState())
        private set

    fun onEvent(event: AddBookUiEvent) {
        when (event) {
            is AddBookUiEvent.OnAuthorChange -> {
                state.update { it.copy(author = event.newValue) }
            }
            is AddBookUiEvent.OnDescriptionChange -> {
                state.update { it.copy(description = event.newValue) }
            }
            is AddBookUiEvent.OnEditionChange -> {
                state.update { it.copy(edition = event.newValue) }
            }
            is AddBookUiEvent.OnGenreChange -> {
                state.update { it.copy(genre = event.newValue) }
            }
            is AddBookUiEvent.OnTitleChange -> {
                state.update { it.copy(title = event.newValue) }
            }
            is AddBookUiEvent.OnYearChange -> {
                state.update { it.copy(year = event.newValue) }
            }
            is AddBookUiEvent.OnChangePhoto -> {
                state.update { it.copy(photoUri = event.uri) }
            }
            AddBookUiEvent.OnSave -> {}
        }
    }

    fun onSaveBook() {
        viewModelScope.launch {
            //            booksRepository.addNewBook(
            //                state.value.photoUri,
            //
            //            )
        }
    }

    override fun onCleared() {
        state.update { AddBookUiState() }
    }
}
