package com.githukudenis.comlib.feature.auth.presentation.login

import com.githukudenis.comlib.feature.auth.presentation.SignInResult

sealed class LoginUiEvent {
    data class ChangeEmail(val email: String): LoginUiEvent()
    data class ChangePassword(val password: String): LoginUiEvent()
    data class DismissUserMessage(val id: Int): LoginUiEvent()
    data class TogglePassword(val isVisible: Boolean): LoginUiEvent()
    data class GoogleSignIn(val signInResult: SignInResult): LoginUiEvent()
    data class ToggleRememberMe(val remember: Boolean): LoginUiEvent()
    data object SubmitData: LoginUiEvent()
}