
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

import com.githukudenis.comlib.core.model.book.AllBooksResponse
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.core.model.book.BooksData

/*
Fake response data to mimic api response
 */
val response: AllBooksResponse =
    AllBooksResponse(
        data =
            BooksData(
                books =
                    listOf(
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
            ),
        requestedAt = "now",
        results = 10,
        status = "success"
    )

val allBooks =
    mutableListOf(
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

class FakeBooksRemoteDataSource {

    suspend fun getBooks(): AllBooksResponse = response

    suspend fun getBook(id: String) = response.data.books.find { it.id == id }

    suspend fun addNewBook(book: Book) = allBooks.add(book)
}
