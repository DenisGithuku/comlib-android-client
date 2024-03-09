package com.githukudenis.comlib.feature.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.DataResult
import com.githukudenis.comlib.core.domain.usecases.GetAllBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetGenresUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase,
    private val getAllBooksUseCase: GetAllBooksUseCase
) : ViewModel() {

    private val genreListUiState: MutableStateFlow<GenreListUiState> =
        MutableStateFlow(GenreListUiState.Loading)

    private val bookListUiState: MutableStateFlow<BookListUiState> =
        MutableStateFlow(BookListUiState.Loading)

    private val selectedGenre: MutableStateFlow<GenreUiModel> =
        MutableStateFlow(GenreUiModel("", ""))


    val uiState: StateFlow<BooksUiState> = combine(
        selectedGenre, genreListUiState, bookListUiState
    ) { selectedGenre, genreState, bookListState ->
        BooksUiState.Success(
            selectedGenre = selectedGenre,
            genreListUiState = genreState,
            bookListUiState = bookListState
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
        getGenresUseCase().collect { result ->
            when (result) {
                DataResult.Empty -> {
                    genreListUiState.update { GenreListUiState.Error(message = "No genres found") }
                }

                is DataResult.Loading -> {
                    genreListUiState.update { GenreListUiState.Loading }
                }

                is DataResult.Success -> {/*
                    map genre to genre ui model
                     */
                    val genres = result.data.map { genre -> genre.toGenreUiModel() }
                    genreListUiState.update { GenreListUiState.Success(genres) }
                }

                is DataResult.Error -> {
                    genreListUiState.update { GenreListUiState.Error(result.message) }
                }
            }
        }
    }

    private suspend fun getBookList() {
        getAllBooksUseCase().collect { result ->
            when (result) {
                DataResult.Empty -> {
                    bookListUiState.update { BookListUiState.Error(message = "No books found") }
                }

                is DataResult.Loading -> {
                    bookListUiState.update { BookListUiState.Loading }
                }

                is DataResult.Success -> {/*
                    map book to book ui model
                     */
                    val books = result.data.map { book -> book.toBookItemUiModel() }
                    bookListUiState.update { BookListUiState.Success(books) }
                }

                is DataResult.Error -> {
                    bookListUiState.update { BookListUiState.Error(result.message) }
                }
            }
        }
    }

    fun onChangeGenre(genreUiModel: GenreUiModel) {
        selectedGenre.update { genreUiModel  }
    }
}