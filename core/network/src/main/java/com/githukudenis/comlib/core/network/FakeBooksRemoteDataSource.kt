package com.githukudenis.comlib.core.network

import com.githukudenis.comlib.core.model.book.AllBooksResponse
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.core.model.book.BooksData
import com.githukudenis.comlib.core.model.genre.Genre

/*
Fake response data to mimic api response
 */
val response: AllBooksResponse = AllBooksResponse(
    data = BooksData(
        books = listOf(
            Book(
                _id = "1",
                authors = emptyList(),
                currentHolder = "userA",
                description = "Good book",
                edition = "first",
                genre_ids = emptyList(),
                id = "1",
                image = "",
                owner = "userA",
                pages = 100,
                reserved = emptyList(),
                title = "Some Good Non-Fiction"
            )
        )
    ), requestedAt = "now", results = 10, status = "success"
)

val allBooks = mutableListOf(
    Book(
        _id = "1",
        authors = emptyList(),
        currentHolder = "userA",
        description = "Good book",
        edition = "first",
        genre_ids = emptyList(),
        id = "1",
        image = "",
        owner = "userA",
        pages = 100,
        reserved = emptyList(),
        title = "Some Good Non-Fiction"
    )
)

val genres = listOf(
    Genre(
        _id = "1", id = "1", name = "Fiction"
    ), Genre(
        _id = "2", id = "2", name = "Non-fiction"
    )
)

class FakeBooksRemoteDataSource {

    suspend fun getBooks(): AllBooksResponse = response
    suspend fun getBook(id: String) = response.data.books.find { it.id == id }
    suspend fun addNewBook(book: Book) = allBooks.add(book)
    suspend fun getGenres() = genres
    suspend fun getGenreById(id: String) = genres.find { it.id == id }
}