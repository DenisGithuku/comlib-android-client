/*
* Copyright 2023 Denis Githuku
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* https://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.githukudenis.comlib.feature.auth.presentation.signup

import com.githukudenis.comlib.core.common.UserMessage
import com.githukudenis.comlib.feature.auth.presentation.common.PasswordRequirements

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
    val acceptedTerms: Boolean = false,
    val isEmailValid: Boolean = false,
    val requirements: List<PasswordRequirements> = emptyList()
) {
    val formIsValid: Boolean
        get() = firstname.trim().isNotEmpty() && lastname.trim().isNotEmpty() && email.trim()
            .isNotEmpty() && password.trim()
            .isNotEmpty() && password == confirmPassword && requirements.containsAll(
            PasswordRequirements.entries.toList()
        ) && acceptedTerms
}
