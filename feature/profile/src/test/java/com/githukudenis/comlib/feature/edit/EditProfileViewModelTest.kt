package com.githukudenis.comlib.feature.edit

import androidx.lifecycle.SavedStateHandle
import androidx.test.filters.MediumTest
import com.githukudenis.comlib.core.domain.usecases.GetUserProfileUseCase
import com.githukudenis.comlib.core.domain.usecases.UpdateUserUseCase
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import com.githukudenis.comlib.data.repository.fake.FakeUserRepository
import com.githukudenis.comlib.feature.profile.edit.EditProfileViewModel
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
    lateinit var updateUserUseCase: UpdateUserUseCase
    lateinit var fakeUserRepository: FakeUserRepository

    @Before
    fun setUp() {
        fakeUserRepository = FakeUserRepository()
        getUserProfileUseCase = GetUserProfileUseCase(
            fakeUserRepository
        )
        updateUserUseCase = UpdateUserUseCase(
            fakeUserRepository
        )
        viewModel = EditProfileViewModel(
            updateUserUseCase,getUserProfileUseCase, SavedStateHandle(mapOf("userId" to "5"))
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
        assertTrue(fakeUserRepository.users.any { it.firstname == "test.firstname" && it.lastname == "test.lastname" })
    }
}