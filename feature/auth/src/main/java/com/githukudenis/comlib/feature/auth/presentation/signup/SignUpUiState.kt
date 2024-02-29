package com.githukudenis.comlib.feature.auth.presentation.signup

import com.githukudenis.comlib.core.common.UserMessage

data class SignUpUiState(
    val isLoading: Boolean = false,
    val signUpSuccess: Boolean = false,
    val formState: SignUpFormState = SignUpFormState(),
    val userMessages: List<UserMessage> = emptyList()
)

data class SignUpFormState(
    val firstname: String = "",
    val lastname: String = "",
    val email: String = "",
    val password: String = "",
    val passwordIsVisible: Boolean = true,
    val confirmPassword: String = "",
    val confirmPasswordIsVisible: Boolean = true,
    val acceptedTerms: Boolean = false
) {
    val formIsValid: Boolean
        get() = firstname.isNotEmpty() &&
                lastname.isNotEmpty() &&
                email.isNotEmpty() &&
                password.isNotEmpty() &&
                confirmPassword.isNotEmpty()
}
