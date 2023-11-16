package com.githukudenis.comlib.data.repository

import com.githukudenis.comlib.core.model.book.AllBooksResponse
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.core.model.book.SingleBookResponse
import com.githukudenis.comlib.core.model.genre.AllGenresResponse
import com.githukudenis.comlib.core.model.genre.Genre
import com.githukudenis.comlib.core.model.genre.SingleGenreResponse
import com.githukudenis.comlib.core.network.BooksRemoteDataSource
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val booksRemoteDataSource: BooksRemoteDataSource,
//    private val booksLocalDataSource: BooksLocalDataSource,
) : BooksRepository {
    override suspend fun getAllBooks(): AllBooksResponse {
        return booksRemoteDataSource.getBooks()
    }

    override suspend fun getBookById(id: String): SingleBookResponse {
        return booksRemoteDataSource.getBook(id)
    }

    override suspend fun addNewBook(book: Book): String {
        return booksRemoteDataSource.addNewBook(book)
    }

    override suspend fun getGenres(): AllGenresResponse {
        return booksRemoteDataSource.getGenres()
    }

    override suspend fun getGenreById(id: String): SingleGenreResponse {
        return booksRemoteDataSource.getGenreById(id)
    }
}