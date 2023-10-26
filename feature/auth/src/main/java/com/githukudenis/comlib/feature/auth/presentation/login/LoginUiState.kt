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
    val firstname: String = "",
    val lastname: String = "",
    val age: Int = 10,
    val password: String = "",
    val confirmPassword: String = "",
    val passwordIsVisible: Boolean = true,
) {
    val formIsValid: Boolean
        get() = email.isNotEmpty() && password.isNotEmpty()
}
