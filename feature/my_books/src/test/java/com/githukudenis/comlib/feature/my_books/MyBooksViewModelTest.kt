package com.githukudenis.comlib.feature.my_books

import androidx.test.filters.MediumTest
import com.githukudenis.comlib.core.domain.usecases.GetBooksByUserUseCase
import com.githukudenis.comlib.core.domain.usecases.GetUserPrefsUseCase
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import com.githukudenis.comlib.data.repository.fake.FakeBooksRepository
import com.githukudenis.comlib.data.repository.fake.FakeUserPrefsRepository
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertEquals

@MediumTest
class MyBooksViewModelTest {

    @get:Rule
    val coroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    lateinit var getBooksByUserUseCase: GetBooksByUserUseCase
    lateinit var userPrefsUseCase: GetUserPrefsUseCase
    lateinit var viewModel: MyBooksViewModel

    @Before
    fun setUp() {
        getBooksByUserUseCase = GetBooksByUserUseCase(
            FakeBooksRepository()
        )
        userPrefsUseCase = GetUserPrefsUseCase(
            FakeUserPrefsRepository()
        )
        viewModel = MyBooksViewModel(getBooksByUserUseCase, userPrefsUseCase)
    }

    @Test
    fun testStateInitialization() = runTest {
        advanceUntilIdle()
        val state = viewModel.state.value
        assertEquals(state.books.size, 1)
    }
}