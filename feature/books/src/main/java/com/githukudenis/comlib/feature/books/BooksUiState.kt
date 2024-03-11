package com.githukudenis.comlib.feature.books

import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.core.model.genre.Genre

sealed class BooksUiState {
    data object Loading : BooksUiState()
    data class Success(
        val selectedGenre: GenreUiModel,
        val bookListUiState: BookListUiState,
        val genreListUiState: GenreListUiState
    ) : BooksUiState()

    data class Error(val message: String) : BooksUiState()
}

sealed class BookListUiState {
    data object Loading : BookListUiState()
    data class Success(val books: List<BookItemUiModel>) : BookListUiState()
    data class Error(val message: String) : BookListUiState()
}

sealed class GenreListUiState {
    data object Loading : GenreListUiState()
    data class Success(
        val genres: List<GenreUiModel>
    ) : GenreListUiState()

    data class Error(val message: String) : GenreListUiState()
}

fun Genre.toGenreUiModel(): GenreUiModel {
    return GenreUiModel(
        name = name, id = id
    )
}

data class GenreUiModel(
    val name: String, val id: String
)

fun Book.toBookItemUiModel(): BookItemUiModel {
    return BookItemUiModel(
        id = id, title = title, authors = authors, imageUrl = image, genres = genre_ids
    )
}

data class BookItemUiModel(
    val id: String,
    val title: String,
    val authors: List<String>,
    val genres: List<String>,
    val imageUrl: String,
)