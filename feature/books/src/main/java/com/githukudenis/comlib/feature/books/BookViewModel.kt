package com.githukudenis.comlib.feature.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.domain.usecases.ComlibUseCases
import com.githukudenis.comlib.core.model.DataResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

class BookViewModel @Inject constructor(
    private val comlibUseCases: ComlibUseCases
) : ViewModel() {

    private val genreListUiState: MutableStateFlow<GenreListUiState> =
        MutableStateFlow(GenreListUiState.Loading)

    private val bookListUiState: MutableStateFlow<BookListUiState> =
        MutableStateFlow(BookListUiState.Loading)

    val uiState: StateFlow<BooksUiState> = combine(
        genreListUiState, bookListUiState
    ) { genreState, bookListState ->
        BooksUiState.Success(
            genreListUiState = genreState, bookListUiState = bookListState
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = BooksUiState.Loading
    )

    init {
        viewModelScope.launch {
            getGenreList()
            getBookList()
        }
    }

    private suspend fun getGenreList() {
        comlibUseCases.getGenresUseCase().collect { result ->
            when (result) {
                DataResult.Empty -> {
                    genreListUiState.value = GenreListUiState.Error(message = "No genres found")
                }

                is DataResult.Loading -> {
                    genreListUiState.value = GenreListUiState.Loading
                }

                is DataResult.Success -> {/*
                    map genre to genre ui model
                     */
                    val genres = result.data.map { genre -> genre.toGenreUiModel() }
                    genreListUiState.value = GenreListUiState.Success(genres)
                }

                is DataResult.Error -> {
                    genreListUiState.value = GenreListUiState.Error(result.message)
                }
            }
        }
    }

    private suspend fun getBookList() {
        comlibUseCases.getAllBooksUseCase().collect { result ->
            when (result) {
                DataResult.Empty -> {
                    bookListUiState.value = BookListUiState.Error(message = "No books found")
                }

                is DataResult.Loading -> {
                    bookListUiState.value = BookListUiState.Loading
                }

                is DataResult.Success -> {/*
                    map book to book ui model
                     */
                    val books = result.data.map { book -> book.toBookItemUiModel() }
                    bookListUiState.value = BookListUiState.Success(books)
                }

                is DataResult.Error -> {
                    bookListUiState.value = BookListUiState.Error(result.message)
                }
            }
        }
    }
}