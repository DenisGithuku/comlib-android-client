package com.githukudenis.comlib.data.repository

import com.githukudenis.comlib.core.model.book.AllBooksResponse
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.core.model.book.SingleBookResponse
import com.githukudenis.comlib.core.model.genre.AllGenresResponse
import com.githukudenis.comlib.core.model.genre.Genre
import com.githukudenis.comlib.core.model.genre.SingleGenreResponse

interface BooksRepository {
    suspend fun getAllBooks(): AllBooksResponse
    suspend fun getBookById(id: String): SingleBookResponse
    suspend fun addNewBook(book: Book): String
    suspend fun getGenres(): AllGenresResponse
    suspend fun getGenreById(id: String): SingleGenreResponse
}