package com.githukudenis.comlib.feature.book_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.domain.usecases.ComlibUseCases
import com.githukudenis.comlib.core.model.DataResult
import com.githukudenis.comlib.core.model.genre.Genre
import com.githukudenis.comlib.data.repository.BooksRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookDetailViewModel @Inject constructor(
    private val comlibUseCases: ComlibUseCases, savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state: MutableStateFlow<BookDetailUiState> =
        MutableStateFlow(BookDetailUiState.Loading)
    val state: StateFlow<BookDetailUiState> = _state.asStateFlow()

    private val bookId: String? = savedStateHandle.get<String>("bookId")

    init {
        savedStateHandle.get<String>("bookId")?.let { bookId ->
            getBook(bookId)
        }
    }

    private fun getBook(bookId: String) {
        viewModelScope.launch {
            comlibUseCases.getBookDetailsUseCase.invoke(bookId).collect { result ->
                when (result) {
                    DataResult.Empty -> Unit
                    is DataResult.Error -> _state.update {
                        BookDetailUiState.Error(result.message)
                    }

                    is DataResult.Loading -> {
                        _state.update { BookDetailUiState.Loading }
                    }

                    is DataResult.Success -> {
                        val isRead =
                            comlibUseCases.getReadBooksUseCase().first().contains(result.data.id)
                        val isFavourite = comlibUseCases.getFavouriteBooksUseCase().first()
                            .contains(result.data.id)
                        _state.update {
                            val bookUiModel = result.data.toBookUiModel(isFavourite = isFavourite,
                                isRead = isRead,
                                getGenre = { genreId ->
                                    fetchGenreById(genreId) ?: Genre(
                                        _id = genreId, id = genreId, name = "Unknown"
                                    )
                                })
                            BookDetailUiState.Success(
                                bookUiModel = bookUiModel,
                            )
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

    private suspend fun fetchGenreById(
        genreId: String
    ): Genre? {
        return comlibUseCases.getGenreByIdUseCase(genreId)
    }
}