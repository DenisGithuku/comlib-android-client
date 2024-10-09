
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
<<<<<<< Updated upstream:core/data/src/main/java/com/githukudenis/comlib/data/repository/fake/FakeBooksRepository.kt
import com.githukudenis.comlib.core.model.book.toBookDTO
import com.githukudenis.comlib.data.repository.BooksRepository
=======
>>>>>>> Stashed changes:core/data/src/main/java/com/githukudenis/comlib/core/data/repository/fake/FakeBooksRepository.kt
import kotlinx.coroutines.delay

class FakeBooksRepository : BooksRepository {
    val books: MutableList<Book> =
        (1..5)
            .map {
                Book(
                        _id = "$it",
                        authors = listOf("Sam", "Peter", "Charlie"),
                        currentHolder = "",
                        edition = "",
                        description = "",
                        genreIds = listOf("1", "2"),
                        id = "$it",
                        image = "",
                        owner = "owner@$it",
                        pages = it,
                        reserved = listOf(),
                        title = "Title $it"
                    )
            }
            .toMutableList()

    enum class MutationResult {
        SUCCESS,
        FAIL
    }

    override suspend fun getAllBooks(): AllBooksResponse {
        delay(1000L)
<<<<<<< Updated upstream:core/data/src/main/java/com/githukudenis/comlib/data/repository/fake/FakeBooksRepository.kt
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
=======
        return ResponseResult.Success(
            AllBooksResponse(
                data = BooksData(books),
                requestedAt = "now",
                results = books.size,
                status = "Ok"
            )
        )
    }

    override suspend fun getBookById(id: String): ResponseResult<SingleBookResponse> {
        return if (books.none { it.id == id }) {
            ResponseResult.Failure(ErrorResponse(status = "fail", message = "Book not found"))
        } else {
            ResponseResult.Success(
                SingleBookResponse(data = Data(book = books.first { it.id == id }), status = "Ok")
            )
        }
>>>>>>> Stashed changes:core/data/src/main/java/com/githukudenis/comlib/core/data/repository/fake/FakeBooksRepository.kt
    }

    override suspend fun addNewBook(imageUri: Uri, book: BookDTO): String {
        return try {
<<<<<<< Updated upstream:core/data/src/main/java/com/githukudenis/comlib/data/repository/fake/FakeBooksRepository.kt
            books.add(book)
            MutationResult.SUCCESS.name
=======
            books.add(book.toBook())
            ResponseResult.Success(AddBookResponse(status = "Ok", message = "Book added successfully"))
>>>>>>> Stashed changes:core/data/src/main/java/com/githukudenis/comlib/core/data/repository/fake/FakeBooksRepository.kt
        } catch (e: Exception) {
            e.printStackTrace()
            MutationResult.FAIL.name
        }
    }

<<<<<<< Updated upstream:core/data/src/main/java/com/githukudenis/comlib/data/repository/fake/FakeBooksRepository.kt
    override suspend fun getBooksByUser(userId: String): List<Book> {
        return books.filter { it.owner == userId }.map { it.toBook() }
=======
    override suspend fun getBooksByUser(userId: String): ResponseResult<BooksByUserResponse> {
        return if (books.none { it.owner == userId }) {
            ResponseResult.Failure(ErrorResponse(status = "fail", message = "Books not found"))
        } else {
            ResponseResult.Success(
                BooksByUserResponse(
                    status = "Ok",
                    requestedAt = "now",
                    results = books.filter { it.owner == userId }.size,
                    data = BooksData(books.filter { it.owner == userId })
                )
            )
        }
>>>>>>> Stashed changes:core/data/src/main/java/com/githukudenis/comlib/core/data/repository/fake/FakeBooksRepository.kt
    }
}
