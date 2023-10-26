package com.githukudenis.comlib.feature.auth.presentation.login

import com.githukudenis.comlib.feature.auth.presentation.SignInResult

sealed class LoginUiEvent {
    data class ChangeEmail(val email: String): LoginUiEvent()
    data class ChangeFirstname(val firstname: String): LoginUiEvent()
    data class ChangeLastname(val lastname: String): LoginUiEvent()
    data class ChangeAge(val age: Int): LoginUiEvent()
    data class ChangePassword(val password: String): LoginUiEvent()
    data class ChangeConfirmPassword(val confirm: String): LoginUiEvent()
    data class DismissUserMessage(val id: Int): LoginUiEvent()
    data class TogglePassword(val isVisible: Boolean): LoginUiEvent()
    data class SignIn(val signInResult: SignInResult): LoginUiEvent()
    data object SubmitData: LoginUiEvent()
}