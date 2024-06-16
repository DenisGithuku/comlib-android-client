package com.githukudenis.comlib.data.repository

import android.net.Uri
import com.githukudenis.comlib.core.model.book.AllBooksResponse
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.core.model.book.SingleBookResponse

interface BooksRepository {
    suspend fun getAllBooks(): AllBooksResponse
    suspend fun getBookById(id: String): SingleBookResponse
    suspend fun addNewBook(imageUri: Uri, book: Book): String
}