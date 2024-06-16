package com.githukudenis.comlib.feature.add_book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.data.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddBookViewModel @Inject constructor(
    private val booksRepository: BooksRepository
): ViewModel() {
    var state = MutableStateFlow(AddBookUiState())
    private set

    fun onEvent(event: AddBookUiEvent) {
        when(event) {
            is AddBookUiEvent.OnAuthorChange -> {
                state.update {
                    it.copy(author = event.newValue)
                }
            }
            is AddBookUiEvent.OnDescriptionChange -> {
                state.update {
                    it.copy(description = event.newValue)
                }
            }
            is AddBookUiEvent.OnEditionChange -> {
                state.update {
                    it.copy(edition = event.newValue)
                }
            }
            is AddBookUiEvent.OnGenreChange -> {
                state.update {
                    it.copy(genre = event.newValue)
                }
            }
            is AddBookUiEvent.OnTitleChange -> {
                state.update {
                    it.copy(title = event.newValue)
                }
            }
            is AddBookUiEvent.OnYearChange -> {
                state.update {
                    it.copy(year = event.newValue)
                }
            }
            is AddBookUiEvent.OnChangePhoto -> {
                state.update {
                    it.copy(photoUri = event.uri)
                }
            }
            AddBookUiEvent.OnSave -> {

            }
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