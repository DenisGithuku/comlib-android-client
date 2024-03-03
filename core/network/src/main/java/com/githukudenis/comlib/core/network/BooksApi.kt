package com.githukudenis.comlib.core.network

import android.util.Log
import com.githukudenis.comlib.core.common.di.ComlibCoroutineDispatchers
import com.githukudenis.comlib.core.model.book.AllBooksResponse
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.core.model.book.SingleBookResponse
import com.githukudenis.comlib.core.network.common.Books
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BooksApi @Inject constructor(
    private val httpClient: HttpClient, private val dispatchers: ComlibCoroutineDispatchers
) {
    suspend fun getBooks(): AllBooksResponse {
        return withContext(dispatchers.io) {
            val books = httpClient.get<AllBooksResponse>(Books.path())
            Log.d("books", books.data.books.toString())
            books
        }
    }

    suspend fun getBookById(bookId: String): SingleBookResponse {
        return withContext(dispatchers.io) {
            val book =
                httpClient.get<SingleBookResponse>("${Books.path()}/$bookId")
            book
        }
    }

    suspend fun addNewBook(book: Book): String {
        return withContext(dispatchers.io) {
            httpClient.post<String>(Books.path()) {
                body = book
            }
        }
    }
}