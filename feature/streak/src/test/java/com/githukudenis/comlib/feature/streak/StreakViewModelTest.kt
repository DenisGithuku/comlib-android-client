package com.githukudenis.comlib.feature.streak

import androidx.lifecycle.SavedStateHandle
import androidx.test.filters.MediumTest
import com.githukudenis.comlib.core.domain.usecases.GetAllBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetReadBooksUseCase
import com.githukudenis.comlib.core.domain.usecases.GetStreakUseCase
import com.githukudenis.comlib.core.domain.usecases.SaveStreakUseCase
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import com.githukudenis.comlib.data.repository.fake.FakeBooksRepository
import com.githukudenis.comlib.data.repository.fake.FakeMilestoneRepository
import com.githukudenis.comlib.data.repository.fake.FakeUserPrefsRepository
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertTrue

@MediumTest
class StreakViewModelTest {

    @get:Rule
    val coroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    lateinit var viewModel: StreakViewModel
    lateinit var getAllBooksUseCase: GetAllBooksUseCase
    lateinit var getReadBooksUseCase: GetReadBooksUseCase
    lateinit var saveStreakUseCase: SaveStreakUseCase
    lateinit var getStreakUseCase: GetStreakUseCase


    @Before
    fun setUp() {
        getAllBooksUseCase = GetAllBooksUseCase(
            FakeBooksRepository()
        )
        getReadBooksUseCase = GetReadBooksUseCase(
            FakeUserPrefsRepository()
        )
        saveStreakUseCase = SaveStreakUseCase(
            FakeMilestoneRepository()
        )
        getStreakUseCase = GetStreakUseCase(
            FakeMilestoneRepository()
        )
        viewModel = StreakViewModel(
            getAllBooksUseCase = getAllBooksUseCase,
            getReadBooksUseCase = getReadBooksUseCase,
            saveStreakUseCase = saveStreakUseCase,
            getStreakUseCase = getStreakUseCase,
            savedStateHandle = SavedStateHandle(
                mapOf("bookId" to "1")
            )
        )
    }

    @Test
    fun testStateInitialization() = runTest {
        advanceUntilIdle()
        assertEquals(viewModel.state.value.selectedBook?.title, "testBook")
    }

    @Test
    fun onToggleBook() = runTest {
        viewModel.onToggleBook(StreakBook(id = "testId", title = "testTitle", pages = 100))
        assertEquals(viewModel.state.value.selectedBook?.pages, 100)
    }

    @Test
    fun onSaveStreak() = runTest {
        viewModel.onSaveStreak()
        advanceUntilIdle()
        assertTrue(viewModel.state.value.saveSuccess)
    }

    @Test
    fun onChangeStartDate() = runTest {
        viewModel.onChangeStartDate(LocalDate(2024, 3, 17))
        assertEquals(viewModel.state.value.startDate, LocalDate(2024, 3, 17))
    }

    @Test
    fun onChangeEndDate() = runTest {
        viewModel.onChangeEndDate(LocalDate(2024, 3, 17))
        assertEquals(viewModel.state.value.endDate, LocalDate(2024, 3, 17))
    }
}