
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
package com.githukudenis.comlib.data.repository.fake

import android.net.Uri
import com.githukudenis.comlib.core.model.book.AllBooksResponse
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.core.model.book.BookDTO
import com.githukudenis.comlib.core.model.book.BooksData
import com.githukudenis.comlib.core.model.book.Data
import com.githukudenis.comlib.core.model.book.SingleBookResponse
import com.githukudenis.comlib.core.model.book.toBook
import com.githukudenis.comlib.core.model.book.toBookDTO
import com.githukudenis.comlib.data.repository.BooksRepository
import kotlinx.coroutines.delay

class FakeBooksRepository : BooksRepository {
    val books: MutableList<BookDTO> =
        (1..5)
            .map {
                Book(
                        _id = "$it",
                        authors = listOf("Sam", "Peter", "Charlie"),
                        currentHolder = "",
                        edition = "",
                        description = "",
                        genre_ids = listOf("1", "2"),
                        id = "$it",
                        image = "",
                        owner = "owner@$it",
                        pages = it,
                        reserved = listOf(),
                        title = "Title $it"
                    )
                    .toBookDTO()
            }
            .toMutableList()

    enum class MutationResult {
        SUCCESS,
        FAIL
    }

    override suspend fun getAllBooks(): AllBooksResponse {
        delay(1000L)
        return AllBooksResponse(
            data = BooksData(books.map { it.toBook() }),
            requestedAt = "now",
            results = books.size,
            status = "Ok"
        )
    }

    override suspend fun getBookById(id: String): SingleBookResponse {
        return SingleBookResponse(
            data = Data(book = books.first { it.id == id }.toBook()),
            status = "Ok"
        )
    }

    override suspend fun addNewBook(image: Uri, book: BookDTO): String {
        return try {
            books.add(book)
            MutationResult.SUCCESS.name
        } catch (e: Exception) {
            e.printStackTrace()
            MutationResult.FAIL.name
        }
    }
}
