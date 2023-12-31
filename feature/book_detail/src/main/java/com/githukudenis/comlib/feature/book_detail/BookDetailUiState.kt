package com.githukudenis.comlib.feature.book_detail

import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.core.model.genre.Genre

sealed class BookDetailUiState {
    data object Loading : BookDetailUiState()
    data class Success(
        val bookUiModel: BookUiModel
    ) : BookDetailUiState()

    data class Error(val message: String) : BookDetailUiState()
}

data class BookUiModel(
    val id: String,
    val title: String,
    val authors: List<String>,
    val genres: List<Genre>,
    val description: String,
    val imageUrl: String,
    val reservedBy: List<String>,
    val pages: Int,
    val isRead: Boolean,
    val isFavourite: Boolean = false
)

suspend fun Book.toBookUiModel( isRead: Boolean, getGenre: suspend (String) -> Genre
): BookUiModel {
    return BookUiModel(
        id = id,
        title = title,
        authors = authors,
        genres = mapGenres(genre_ids, block = { genreId ->
            getGenre(genreId)
        }),
        description = description,
        imageUrl = image,
        isRead = isRead,
        reservedBy = reserved,
        pages = pages
    )
}

suspend fun mapGenres(genreIds: List<String>, block: suspend (String) -> Genre): List<Genre> {
    return genreIds.map { id -> block(id) }
}