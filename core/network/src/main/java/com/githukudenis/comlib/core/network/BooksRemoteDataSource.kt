package com.githukudenis.comlib.core.network

import com.githukudenis.comlib.core.model.book.Book
import javax.inject.Inject

class BooksRemoteDataSource @Inject constructor(private val booksApi: BooksApi) {
    suspend fun getBooks() = booksApi.getBooks()
    suspend fun getBook(id: String) = booksApi.getBookById(id)
    suspend fun addNewBook(book: Book) = booksApi.addNewBook(book)
}