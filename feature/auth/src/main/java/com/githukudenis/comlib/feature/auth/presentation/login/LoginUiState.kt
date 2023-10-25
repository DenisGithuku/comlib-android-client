package com.githukudenis.comlib.feature.auth.presentation.login

import com.githukudenis.comlib.core.common.UserMessage

data class LoginUiState (
    val isLoading: Boolean = false,
    val formState: FormState = FormState(),
    val loginSuccess: Boolean = false,
    val userMessages: List<UserMessage> = emptyList()
)

data class FormState(
    val email: String? = null,
    val firstname: String? = null,
    val lastname: String? = null,
    val age: Int? = null,
    val password: String? = null,
    val confirmPassword: String? = null,
)
