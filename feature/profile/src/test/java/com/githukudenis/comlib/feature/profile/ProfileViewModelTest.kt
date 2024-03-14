package com.githukudenis.comlib.feature.profile

import androidx.test.filters.MediumTest
import com.githukudenis.comlib.core.domain.usecases.GetUserPrefsUseCase
import com.githukudenis.comlib.core.domain.usecases.GetUserProfileUseCase
import com.githukudenis.comlib.core.domain.usecases.SignOutUseCase
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import com.githukudenis.comlib.data.repository.fake.FakeAuthRepository
import com.githukudenis.comlib.data.repository.fake.FakeUserPrefsRepository
import com.githukudenis.comlib.data.repository.fake.FakeUserRepository
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import kotlin.test.Test
import kotlin.test.assertTrue

@MediumTest
class ProfileViewModelTest {
    @get:Rule
    val coroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    lateinit var viewModel: ProfileViewModel
    lateinit var getUserPrefsUseCase: GetUserPrefsUseCase
    lateinit var getUserProfileUseCase: GetUserProfileUseCase
    lateinit var signOutUseCase: SignOutUseCase

    @Before
    fun setup() {
        getUserPrefsUseCase = GetUserPrefsUseCase(
            FakeUserPrefsRepository()
        )
        getUserProfileUseCase = GetUserProfileUseCase(
            FakeUserRepository()
        )
        signOutUseCase = SignOutUseCase(
            FakeAuthRepository()
        )
        viewModel = ProfileViewModel(
            getUserPrefsUseCase, getUserProfileUseCase, signOutUseCase
        )
    }

    @Test
    fun testStateInitialization() = runTest {
        val state = viewModel.uiState.value
        assert(state.profile?.email == "5@gmail.com")
    }

    @Test
    fun onSignOut() = runTest {
        viewModel.onSignOut()
        assertTrue(viewModel.uiState.value.isSignedOut)
    }

    @Test
    fun onToggleCache() = runTest {
        viewModel.onToggleCache(true)
        assertTrue(viewModel.uiState.value.isClearCache)
    }

    @Test
    fun onToggleSignOut() = runTest {
        viewModel.onToggleSignOut(true)
        assertTrue(viewModel.uiState.value.isSignout)
    }
}