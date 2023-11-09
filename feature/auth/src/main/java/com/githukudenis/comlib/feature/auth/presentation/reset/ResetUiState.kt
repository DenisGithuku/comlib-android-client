package com.githukudenis.comlib.feature.auth.presentation.reset

data class ResetUiState(
    val email: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
) {
    val isEmailValid: Boolean
        get() = email.isNotEmpty() && email.contains('@')
}
