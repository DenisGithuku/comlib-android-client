
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
package com.githukudenis.comlib.feature.my_books

import androidx.test.filters.MediumTest
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import com.githukudenis.comlib.data.repository.fake.FakeBooksRepository
import com.githukudenis.comlib.data.repository.fake.FakeUserPrefsRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule

@MediumTest
class MyBooksViewModelTest {

    @get:Rule val coroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    lateinit var booksRepository: FakeBooksRepository
    lateinit var userPrefsRepository: FakeUserPrefsRepository
    lateinit var viewModel: MyBooksViewModel

    @Before
    fun setUp() {
        booksRepository = FakeBooksRepository()
        userPrefsRepository = FakeUserPrefsRepository()
        viewModel =
            MyBooksViewModel(userPrefsRepository = userPrefsRepository, booksRepository = booksRepository)
    }

    @Test
    fun testStateInitialization() = runTest {
        advanceUntilIdle()
        val state = viewModel.state.value
        assertEquals(state.books.size, 1)
    }
}
