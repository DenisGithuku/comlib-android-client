package com.githukudenis.comlib.feature.auth.presentation.login

import com.githukudenis.comlib.core.common.UserMessage

data class LoginUiState (
    val isLoading: Boolean = false,
    val formState: FormState = FormState(),
    val loginSuccess: Boolean = false,
    val userMessages: List<UserMessage> = emptyList()
)

data class FormState(
    val email: String = "",
    val password: String = "",
    val passwordIsVisible: Boolean = true,
) {
    val formIsValid: Boolean
        get() = email.isNotEmpty() && password.isNotEmpty()
}
