package com.githukudenis.comlib.feature.auth.presentation.signup

import com.githukudenis.comlib.feature.auth.presentation.SignInResult
import com.githukudenis.comlib.feature.auth.presentation.login.LoginUiEvent

sealed class SignUpUiEvent {
    data class ChangeFirstname(val firstname: String) : SignUpUiEvent()
    data class ChangeLastname(val lastname: String) : SignUpUiEvent()
    data class ChangeEmail(val email: String) : SignUpUiEvent()
    data class ChangeAge(val age: String) : SignUpUiEvent()
    data class ChangePassword(val password: String) : SignUpUiEvent()
    data class TogglePasswordVisibility(val isVisible: Boolean) : SignUpUiEvent()
    data class ChangeConfirmPassword(val confirmPassword: String) : SignUpUiEvent()
    data class ToggleConfirmPasswordVisibility(val isVisible: Boolean) : SignUpUiEvent()
    data class DismissUserMessage(val id: Int) : SignUpUiEvent()
    data class GoogleSignIn(val signInResult: SignInResult) : SignUpUiEvent()
    data object Submit : SignUpUiEvent()
}