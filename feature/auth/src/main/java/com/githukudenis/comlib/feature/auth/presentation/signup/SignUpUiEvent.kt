
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

import com.githukudenis.comlib.feature.auth.presentation.SignInResult

sealed class SignUpUiEvent {
    data class ChangeFirstname(val firstname: String) : SignUpUiEvent()

    data class ChangeLastname(val lastname: String) : SignUpUiEvent()

    data class ChangeEmail(val email: String) : SignUpUiEvent()

    data class ChangePassword(val password: String) : SignUpUiEvent()

    data class TogglePasswordVisibility(val isVisible: Boolean) : SignUpUiEvent()

    data class ChangeConfirmPassword(val confirmPassword: String) : SignUpUiEvent()

    data class ToggleConfirmPasswordVisibility(val isVisible: Boolean) : SignUpUiEvent()

    data class DismissUserMessage(val id: Int) : SignUpUiEvent()

    data class GoogleSignIn(val signInResult: SignInResult) : SignUpUiEvent()

    data class ToggleTerms(val accepted: Boolean) : SignUpUiEvent()

    data object Submit : SignUpUiEvent()
}
