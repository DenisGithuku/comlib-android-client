package com.githukudenis.comlib.data.repository.fake

import android.net.Uri
import com.githukudenis.comlib.core.model.book.AllBooksResponse
import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.core.model.book.BooksData
import com.githukudenis.comlib.core.model.book.Data
import com.githukudenis.comlib.core.model.book.SingleBookResponse
import com.githukudenis.comlib.data.repository.BooksRepository
import kotlinx.coroutines.delay

class FakeBooksRepository: BooksRepository {
    val books: MutableList<Book> = (1..5).map {
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
    }.toMutableList()

    enum class MutationResult {
        SUCCESS,
        FAIL
    }



    override suspend fun getAllBooks(): AllBooksResponse {
        delay(1000L)
        return AllBooksResponse(
            data = BooksData(books),
            requestedAt = "now",
            results = books.size,
            status = "Ok"
        )
    }

    override suspend fun getBookById(id: String): SingleBookResponse {
        return SingleBookResponse(
            data = Data(book = books.first { it.id == id }),
            status = "Ok"
        )
    }

    override suspend fun addNewBook(
        image: Uri,
        book: Book): String {
        return try {
            books.add(book)
            MutationResult.SUCCESS.name
        } catch (e: Exception) {
            e.printStackTrace()
            MutationResult.FAIL.name
        }
    }
}