package com.githukudenis.comlib.feature.auth.presentation.login

sealed class LoginUiEvent {
    data class ChangeEmail(val email: String): LoginUiEvent()
    data class ChangeFirstname(val firstname: String): LoginUiEvent()
    data class ChangeLastname(val lastname: String): LoginUiEvent()
    data class ChangeAge(val age: Int): LoginUiEvent()
    data class ChangePassword(val password: String): LoginUiEvent()
    data class ChangeConfirmPassword(val confirm: String): LoginUiEvent()
    data class DismissUserMessage(val id: Int): LoginUiEvent()
    data object SubmitData: LoginUiEvent()
}