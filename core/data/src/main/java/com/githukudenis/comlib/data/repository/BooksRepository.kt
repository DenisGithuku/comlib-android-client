package com.githukudenis.comlib.data.repository

import com.githukudenis.comlib.core.model.book.AllBooksResponse
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.core.model.book.SingleBookResponse

interface BooksRepository {
    suspend fun getAllBooks(): AllBooksResponse

    suspend fun getBookById(id: String): SingleBookResponse

    suspend fun addNewBook(book: Book): String
}