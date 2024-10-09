
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
package com.githukudenis.comlib.feature.auth.presentation.login

import androidx.test.filters.MediumTest
import com.githukudenis.comlib.core.common.MessageType
import com.githukudenis.comlib.core.common.UserMessage
import com.githukudenis.comlib.core.data.repository.fake.FakeAuthRepository
import com.githukudenis.comlib.core.data.repository.fake.FakeUserPrefsRepository
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@MediumTest
class LoginViewModelTest {
    @get:Rule val coroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel =
            LoginViewModel(
                authRepository = FakeAuthRepository(),
                userPrefsRepository = FakeUserPrefsRepository()
            )
    }

    @Test
    fun testOnChangeEmail() = runTest {
        viewModel.onEvent(LoginUiEvent.ChangeEmail("newmail@mail.com"))
        assertEquals(viewModel.state.value.formState.email, "newmail@mail.com")
    }

    @Test
    fun testOnChangePassword() = runTest {
        viewModel.onEvent(LoginUiEvent.ChangePassword("newpass"))
        assertEquals(viewModel.state.value.formState.password, "newpass")
    }

    @Test
    fun testTogglePasswordVisibility() = runTest {
        viewModel.onEvent(LoginUiEvent.TogglePassword(true))
        assertTrue(viewModel.state.value.formState.passwordIsVisible)
    }

    @Test
    fun testToggleRememberMe() = runTest {
        viewModel.onEvent(LoginUiEvent.ToggleRememberMe(true))
        assertTrue(viewModel.state.value.formState.rememberMe)
    }

    @Test
    fun testResetState() = runTest {
        viewModel.onEvent(LoginUiEvent.ResetState)
        assertEquals(
            viewModel.state.value,
            LoginUiState(
                isLoading = false,
                formState = FormState(),
                loginSuccess = false,
                userMessages = emptyList()
            )
        )
    }

    @Test
    fun testLoginWithEmptyEmailThrowsError() = runTest {
        viewModel.onEvent(LoginUiEvent.ChangePassword("pass1234"))
        viewModel.onEvent(LoginUiEvent.SubmitData)
        assertEquals(
            viewModel.state.value.userMessages.first(),
            UserMessage(message = "Please check your details", messageType = MessageType.ERROR)
        )
    }

    @Test
    fun testLoginWithValidDetailsReturnsSuccess() = runTest {
        // create an empty collector
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.state.collect() }
        viewModel.onEvent(LoginUiEvent.ChangeEmail("william.henry.moody@my-own-personal-domain.com"))
        viewModel.onEvent(LoginUiEvent.ChangePassword("password"))
        viewModel.onEvent(LoginUiEvent.SubmitData)
        advanceUntilIdle()
        assertTrue(viewModel.state.value.loginSuccess)
    }

    @Test
    fun testLoginWithInvalidDetailsReturnsError() = runTest {
        // create an empty collector
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.state.collect() }
        viewModel.onEvent(LoginUiEvent.ChangeEmail("james.c.mcreynolds@example-pet-store.com"))
        viewModel.onEvent(LoginUiEvent.ChangePassword("password"))
        viewModel.onEvent(LoginUiEvent.SubmitData)
        advanceUntilIdle()
        assertEquals(
            viewModel.state.value.userMessages.first(),
            UserMessage(message = "Could not find user with those details")
        )
        assertEquals(viewModel.state.value.loginSuccess, false)
    }
}
