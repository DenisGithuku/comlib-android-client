package com.githukudenis.comlib.feature.auth.presentation.signup

import androidx.test.filters.MediumTest
import com.githukudenis.comlib.core.domain.usecases.SignUpUseCase
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import com.githukudenis.comlib.data.repository.fake.FakeAuthRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals


@MediumTest
class SignUpViewModelTest {

    @get:Rule
    val coroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    lateinit var viewModel: SignUpViewModel
    lateinit var signUpUseCase: SignUpUseCase

    @Before
    fun setUp() {
       signUpUseCase = SignUpUseCase(
           FakeAuthRepository()
       )
        viewModel = SignUpViewModel(signUpUseCase)
    }

    @Test
    fun testOnChangeEmail() = runTest {
        viewModel.onEvent(SignUpUiEvent.ChangeEmail("testemail@gmail.com"))
        assertEquals(
            viewModel.state.value.formState.email,
            "testemail@gmail.com"
        )
    }

    @Test
    fun testOnChangeFirstname() = runTest {
        viewModel.onEvent(SignUpUiEvent.ChangeFirstname("firstname"))
        assertEquals(
            viewModel.state.value.formState.firstname,
            "firstname"
        )
    }

    @Test
    fun testOnChangeLastname() = runTest {
        viewModel.onEvent(SignUpUiEvent.ChangeLastname("lastname"))
        assertEquals(
            viewModel.state.value.formState.lastname,
            "lastname"
        )
    }

    @Test
    fun testOnPasswordChange() = runTest {
        viewModel.onEvent(SignUpUiEvent.ChangePassword("newpass"))
        assertEquals(
            viewModel.state.value.formState.password,
            "newpass"
        )
    }

    @Test
    fun testOnTogglePasswordVisibility() = runTest {
        viewModel.onEvent(SignUpUiEvent.TogglePasswordVisibility(false))
        viewModel.onEvent(SignUpUiEvent.TogglePasswordVisibility(true))

        assertTrue(viewModel.state.value.formState.passwordIsVisible)
    }

    @Test
    fun testOnToggleConfirmPasswordVisibility() = runTest {
        viewModel.onEvent(SignUpUiEvent.ToggleConfirmPasswordVisibility(true))
        viewModel.onEvent(SignUpUiEvent.ToggleConfirmPasswordVisibility(false))

        assertFalse(viewModel.state.value.formState.confirmPasswordIsVisible)
    }

    @Test
    fun testToggleTerms() = runTest {
        viewModel.onEvent(
            SignUpUiEvent.ToggleTerms(true)
        )
        assertTrue(viewModel.state.value.formState.acceptedTerms)
    }
}