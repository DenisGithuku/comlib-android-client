package com.githukudenis.comlib.feature.auth.presentation.reset

import com.githukudenis.comlib.core.common.UserMessage

data class ResetUiState(
    val email: String = "",
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: UserMessage? = null
) {
    val isEmailValid: Boolean
        get() = email.isNotEmpty() && email.contains('@')
}
