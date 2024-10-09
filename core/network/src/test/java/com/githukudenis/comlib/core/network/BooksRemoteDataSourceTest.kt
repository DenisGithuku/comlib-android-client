
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

import com.githukudenis.comlib.core.model.book.Book
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import kotlin.test.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BooksRemoteDataSourceTest {

    @get:Rule val mainCoroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

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
                genreIds = emptyList(),
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
