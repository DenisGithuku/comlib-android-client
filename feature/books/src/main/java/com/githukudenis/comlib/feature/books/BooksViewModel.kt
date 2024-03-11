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
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BooksViewModel @Inject constructor(
    private val getGenresUseCase: GetGenresUseCase,
    private val getAllBooksUseCase: GetAllBooksUseCase
) : ViewModel() {

    val moreGenreModel = GenreUiModel(
        "More", "65eebf0badf8c6d9a1d1db48"
    )

    private val genreListUiState: MutableStateFlow<GenreListUiState> =
        MutableStateFlow(GenreListUiState.Loading)

    private val bookListUiState: MutableStateFlow<BookListUiState> =
        MutableStateFlow(BookListUiState.Loading)

    private val selectedGenres: MutableStateFlow<List<GenreUiModel>> =
        MutableStateFlow(listOf(GenreUiModel(
            name = "All",
            id = "65eeb125703fed5c184518bf"
        )))



    val uiState: StateFlow<BooksUiState> = combine(
        selectedGenres, genreListUiState, bookListUiState
    ) { selectedGenres, genreState, bookListState ->
        BooksUiState.Success(
            selectedGenres = selectedGenres,
            genreListUiState = genreState,
            bookListUiState = bookListState,
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
                    val genres = result.data
                        .map { genre -> genre.toGenreUiModel() }
                        .sortedBy { it.name }.toMutableList()

                    genres.add(
                        0, selectedGenres.value.first()
                    )
                    genres.add(
                        genres.size, moreGenreModel
                    )
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
                    val books = result.data.filter { book ->
                        if (selectedGenres.value.map { it.name }.contains("All")) {
                            true
                        } else {
                            selectedGenres.value.map { it.id }.any { it in book.genre_ids }
                        }
                    }.map { book -> book.toBookItemUiModel() }.sortedBy { it.title }

                    val updatedState = if(books.isEmpty()) BookListUiState.Empty else BookListUiState.Success(books)

                    bookListUiState.update { updatedState }
                }

                is DataResult.Error -> {
                    bookListUiState.update { BookListUiState.Error(result.message) }
                }
            }
        }
    }

    fun onChangeGenre(id: String) {
        viewModelScope.launch {
            when (val genreState = genreListUiState.value) {
                is GenreListUiState.Error -> Unit
                GenreListUiState.Loading -> Unit
                is GenreListUiState.Success -> {
                    if (id == moreGenreModel.id) return@launch

                    val updatedGenre = genreState.genres.first {
                        it.id == id
                    }

                    val selected = selectedGenres.first().toMutableList()

                    if (id in selectedGenres.first().map { it.id }) {
                        selected.remove(updatedGenre)
                    } else {
                        selected.add(updatedGenre)
                    }

                    selectedGenres.update { selected }
                    getBookList()
                }
            }
        }
    }
}