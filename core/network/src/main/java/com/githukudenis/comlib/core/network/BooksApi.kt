package com.githukudenis.comlib.core.network

import android.util.Log
import com.githukudenis.comlib.core.common.di.ComlibCoroutineDispatchers
import com.githukudenis.comlib.core.model.book.AllBooksResponse
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.core.model.book.SingleBookResponse
import com.githukudenis.comlib.core.model.genre.AllGenresResponse
import com.githukudenis.comlib.core.model.genre.SingleGenreResponse
import com.githukudenis.comlib.core.network.common.Constants
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
            val books = httpClient.get<AllBooksResponse>("${Constants.BASE_URL}/${Constants.BOOKS_ENDPOINT}")
            Log.d("books", books.data.books.toString())
            books
        }
    }

    suspend fun getBookById(bookId: String): SingleBookResponse {
        return withContext(dispatchers.io) {
            val book =
                httpClient.get<SingleBookResponse>("${Constants.BASE_URL}/${Constants.BOOKS_ENDPOINT}/$bookId")
            book
        }
    }

    suspend fun addNewBook(book: Book): String {
        return withContext(dispatchers.io) {
            httpClient.post<String>("${Constants.BASE_URL}/${Constants.BOOKS_ENDPOINT}") {
                body = book
            }
        }
    }

    suspend fun getGenres(): AllGenresResponse {
        return withContext(dispatchers.io) {
            httpClient.get<AllGenresResponse>("${Constants.BASE_URL}/${Constants.GENRES_ENDPOINT}")
        }
    }

    suspend fun getGenreById(genreId: String): SingleGenreResponse {
        return withContext(dispatchers.io) {
            httpClient.get<SingleGenreResponse>("${Constants.BASE_URL}/${Constants.GENRES_ENDPOINT}/$genreId")
        }
    }
}