
/*
* Copyright 2023 Denis Githuku
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.githukudenis.comlib.core.network

import android.util.Log
import com.githukudenis.comlib.core.common.di.ComlibCoroutineDispatchers
import com.githukudenis.comlib.core.model.book.AllBooksResponse
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.core.model.book.SingleBookResponse
import com.githukudenis.comlib.core.network.common.Endpoints
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import javax.inject.Inject
import kotlinx.coroutines.withContext

class BooksApi
@Inject
constructor(
    private val httpClient: HttpClient,
    private val dispatchers: ComlibCoroutineDispatchers
) {
    suspend fun getBooks(): AllBooksResponse {
        return withContext(dispatchers.io) {
            val books = httpClient.get<AllBooksResponse>(Endpoints.Books.url)
            Log.d("books", books.data.books.toString())
            books
        }
    }

    suspend fun getBookById(bookId: String): SingleBookResponse {
        return withContext(dispatchers.io) {
            val book = httpClient.get<SingleBookResponse>(Endpoints.Book(bookId).url)
            book
        }
    }

    suspend fun addNewBook(book: Book): String {
        return withContext(dispatchers.io) {
            httpClient.post<String>(Endpoints.Books.url) { body = book }
        }
    }
}
