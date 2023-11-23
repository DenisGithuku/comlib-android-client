package com.githukudenis.comlib.core.network

import com.githukudenis.comlib.core.model.book.Book
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals

class BooksRemoteDataSourceTest {
    private val fakeBooksRemoteDataSource = FakeBooksRemoteDataSource()

    private val testScope = TestScope(UnconfinedTestDispatcher())

    @Test
    fun getBooks() = testScope.runTest {
        val result = fakeBooksRemoteDataSource.getBooks()

        assertEquals(result.data.books.size, 1)
    }

    @Test
    fun getBook() = testScope.runTest {
        val result = fakeBooksRemoteDataSource.getBook("1")
        assertEquals(result?.description, "Good book")
    }

    @Test
    fun addNewBook() = testScope.runTest {
        fakeBooksRemoteDataSource.addNewBook(
            Book(
                _id = "2",
                authors = emptyList(),
                currentHolder = "userB",
                description = "Good book",
                edition = "first",
                genre_ids = emptyList(),
                id = "2",
                image = "",
                owner = "userA",
                pages = 120,
                reserved = emptyList(),
                title = "Some Good Fiction"
            )
        )
        assertEquals(allBooks.size, 2)
    }

    @Test
    fun getGenres() = testScope.runTest {
        val result = fakeBooksRemoteDataSource.getGenres()
        assertEquals(result.size, 2)
    }

    @Test
    fun getGenreById() = testScope.runTest {
        val result = fakeBooksRemoteDataSource.getGenreById("2")
        assertEquals(result?.name, "Non-fiction")

    }
}