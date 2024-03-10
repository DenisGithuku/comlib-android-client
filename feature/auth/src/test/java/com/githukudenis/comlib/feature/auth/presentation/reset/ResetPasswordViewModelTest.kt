package com.githukudenis.comlib.feature.auth.presentation.reset

import androidx.test.filters.MediumTest
import com.githukudenis.comlib.core.testing.util.MainCoroutineRule
import com.githukudenis.comlib.data.repository.fake.FakeAuthRepository
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

@MediumTest
class ResetPasswordViewModelTest {

    @get:Rule
    val coroutineRule: MainCoroutineRule by lazy { MainCoroutineRule() }

    lateinit var viewModel: ResetPasswordViewModel

    @Before
    fun setUp() {
        viewModel = ResetPasswordViewModel(
            FakeAuthRepository()
        )
    }

    @Test
    fun onEmailChange() = runTest {
        viewModel.onEmailChange("newmail@mail.com")
        assertEquals(
            viewModel.state.value.email,
            "newmail@mail.com"
        )
    }

    @Test
    fun onResetWithWrongDetailsReturnsError() = runTest {
        viewModel.onEmailChange("wrong.test.mail@mail.com")
        viewModel.onReset()
        assertEquals(
            viewModel.state.value.error?.message,
            "Couldn't find that user!"
        )
    }

    @Test
    fun onResetWithValidEmailReturnsSuccess() = runTest {
        viewModel.onEmailChange("william.henry.moody@my-own-personal-domain.com")
        viewModel.onReset()
        assertTrue(
            viewModel.state.value.isSuccess
        )
    }

    @Test
    fun onDismissError() = runTest {
        viewModel.onEmailChange("wrong.test.mail@mail.com")
        viewModel.onReset()
        viewModel.onErrorShown()
        assertNull(
            viewModel.state.value.error
        )
    }
}