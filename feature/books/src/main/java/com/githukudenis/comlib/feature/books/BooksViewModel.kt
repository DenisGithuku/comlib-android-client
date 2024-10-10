
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
package com.githukudenis.comlib.feature.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.data.repository.BooksRepository
import com.githukudenis.comlib.core.data.repository.GenresRepository
import com.githukudenis.comlib.core.data.repository.UserPrefsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class BooksViewModel
@Inject
constructor(
    private val userPrefsRepository: UserPrefsRepository,
    private val booksRepository: BooksRepository,
    private val genresRepository: GenresRepository
) : ViewModel() {

    private val _pagingData: MutableStateFlow<Pair<Int, Int>> = MutableStateFlow(Pair(1, 10))

    private val moreGenreModel = GenreUiModel("More", "65eebf0badf8c6d9a1d1db48")

    private val genreListUiState: MutableStateFlow<GenreListUiState> =
        MutableStateFlow(GenreListUiState.Loading)

    private val bookListUiState: MutableStateFlow<BookListUiState> =
        MutableStateFlow(BookListUiState.Loading)

    private val selectedGenres: MutableStateFlow<List<GenreUiModel>> =
        MutableStateFlow(listOf(GenreUiModel(name = "All Genres", id = "65eeb125703fed5c184518bf")))

    val uiState: StateFlow<BooksUiState> =
        combine(selectedGenres, genreListUiState, bookListUiState) {
                selectedGenres,
                genreState,
                bookListState ->
                BooksUiState.Success(
                    selectedGenres = selectedGenres,
                    genreListUiState = genreState,
                    bookListUiState = bookListState
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = BooksUiState.Loading
            )

    init {
        getGenreList()
        getBookList()
    }

    private fun getGenreList() {
        viewModelScope.launch {
            genreListUiState.update { GenreListUiState.Loading }
            when (val result = genresRepository.getGenres()) {
                is ResponseResult.Failure -> {
                    genreListUiState.update { GenreListUiState.Error(message = result.error.message) }
                }
                is ResponseResult.Success -> {
                    // map genre to genre ui model

                    val genres =
                        result.data.data.genres
                            .map { genre -> genre.toGenreUiModel() }
                            .sortedBy { it.name }
                            .toMutableList()

                    genres.add(0, selectedGenres.value.first())
                    genres.add(genres.size, moreGenreModel)
                    getPreferredGenreList(genres)
                    genreListUiState.update { GenreListUiState.Success(genres) }
                }
            }
        }
    }

    private fun getBookList() {
        viewModelScope.launch {
            bookListUiState.update { BookListUiState.Loading }
            when (val result = booksRepository.getAllBooks(_pagingData.value.first, _pagingData.value.second)) {
                is ResponseResult.Failure -> {
                    bookListUiState.update { BookListUiState.Error(message = result.error.message) }
                }
                is ResponseResult.Success -> {
                    // map book to book ui model

                    val books =
                        result.data.data.books
                            .filter { book ->
                                if (selectedGenres.value.map { it.name }.contains("All Genres")) {
                                    true
                                } else {
                                    selectedGenres.value.map { it.id }.any { it in book.genreIds }
                                }
                            }
                            .map { book -> book.toBookItemUiModel() }
                            .sortedBy { it.title }

                    val updatedState =
                        if (books.isEmpty()) {
                            BookListUiState.Empty
                        } else BookListUiState.Success(books)

                    bookListUiState.update { updatedState }
                }
            }
        }
    }

    private fun getPreferredGenreList(genres: List<GenreUiModel>) {
        viewModelScope.launch {
            userPrefsRepository.userPrefs
                .mapLatest { it.preferredGenres }
                .collectLatest { preferred ->
                    selectedGenres.update { prevState ->
                        val newList = prevState.toMutableList()
                        newList.addAll(genres.filter { it.id in preferred })
                        newList
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

                    val updatedGenre = genreState.genres.first { it.id == id }

                    val selected = selectedGenres.first().toMutableList()

                    if (id in selectedGenres.first().map { it.id }) {
                        selected.remove(updatedGenre)
                    } else if (id == "65eeb125703fed5c184518bf") {
                        selected.clear()
                        selected.add(updatedGenre)
                    } else {
                        selected.add(updatedGenre)
                    }

                    val updatedGenrePrefs =
                        selected.dropWhile { it.id != "65eeb125703fed5c184518bf" }.map { it.id }.toSet()
                    userPrefsRepository.setPreferredGenres(updatedGenrePrefs)
                    selectedGenres.update { selected }
                    getBookList()
                }
            }
        }
    }
}
