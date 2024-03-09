package com.githukudenis.comlib.core.network

import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

class BooksRemoteDataSourceTest {

    @get:Rule
    val mainCoroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    lateinit var fakeBooksRemoteDataSource: FakeBooksRemoteDataSource


    @Before
    fun setup() {
        fakeBooksRemoteDataSource = FakeBooksRemoteDataSource()
    }

    @Test
    fun getBooks() = runTest {
        val result = fakeBooksRemoteDataSource.getBooks()

        assertEquals(result.data.books.size, 1)
    }

    @Test
    fun getBook() = runTest {
        val result = fakeBooksRemoteDataSource.getBook("1")
        assertEquals(result?.description, "Good book")
    }

    @Test
    fun addNewBook() = runTest {
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
}