package com.githukudenis.comlib.feature.edit

import androidx.test.filters.MediumTest
import com.githukudenis.comlib.core.domain.usecases.GetUserPrefsUseCase
import com.githukudenis.comlib.core.domain.usecases.GetUserProfileUseCase
import com.githukudenis.comlib.core.domain.usecases.UpdateUserUseCase
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import com.githukudenis.comlib.data.repository.fake.FakeUserPrefsRepository
import com.githukudenis.comlib.data.repository.fake.FakeUserRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@MediumTest
class EditProfileViewModelTest {

    @get:Rule
    val coroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    lateinit var viewModel: EditProfileViewModel
    lateinit var getUserProfileUseCase: GetUserProfileUseCase
    lateinit var getUserPrefsUseCase: GetUserPrefsUseCase
    lateinit var updateUserUseCase: UpdateUserUseCase
    lateinit var userRepository: FakeUserRepository
    lateinit var userPrefsRepository: FakeUserPrefsRepository

    @Before
    fun setUp() {
        userRepository = FakeUserRepository()
        userPrefsRepository = FakeUserPrefsRepository()
        getUserProfileUseCase = GetUserProfileUseCase(
            userRepository
        )
        updateUserUseCase = UpdateUserUseCase(
            userRepository
        )
        getUserPrefsUseCase = GetUserPrefsUseCase(
            userPrefsRepository
        )
        viewModel = EditProfileViewModel(
            updateUserUseCase,
            getUserProfileUseCase,
            getUserPrefsUseCase
        )
    }

    @Test
    fun testStateInitialization() = runTest {
        advanceUntilIdle()
        assertEquals(viewModel.state.value.firstname, "5.firstname")
    }

    @Test
    fun testUpdateUser() = runTest {
        viewModel.update { copy(firstname = "test.firstname", lastname = "test.lastname") }
        viewModel.updateUser()
        assertTrue(userRepository.users.any { it.firstname == "test.firstname" && it.lastname == "test.lastname" })
    }
}