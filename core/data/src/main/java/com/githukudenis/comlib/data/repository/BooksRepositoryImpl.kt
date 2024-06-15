package com.githukudenis.comlib.data.repository

import android.net.Uri
import com.githukudenis.comlib.core.model.book.AllBooksResponse
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.core.model.book.SingleBookResponse
import com.githukudenis.comlib.core.network.BooksRemoteDataSource
import com.githukudenis.comlib.core.network.ImagesRemoteDataSource
import javax.inject.Inject

class BooksRepositoryImpl @Inject constructor(
    private val booksRemoteDataSource: BooksRemoteDataSource,
    private val imagesRemoteDataSource: ImagesRemoteDataSource
//    private val booksLocalDataSource: BooksLocalDataSource,
) : BooksRepository {
    override suspend fun getAllBooks(): AllBooksResponse {
        return booksRemoteDataSource.getBooks()
    }

    override suspend fun getBookById(id: String): SingleBookResponse {
        return booksRemoteDataSource.getBook(id)
    }

    override suspend fun addNewBook(imageUri: Uri, book: Book): String {
        val bookImage = imagesRemoteDataSource.addImage(imageUri)
        return bookImage.getOrNull()?.let { imagePath ->
            val updatedBook = book.copy(image = imagePath)
            booksRemoteDataSource.addNewBook(updatedBook)
        } ?: ""
    }
}