
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
package com.githukudenis.comlib.feature.auth.presentation.login

import com.githukudenis.comlib.feature.auth.presentation.SignInResult

sealed class LoginUiEvent {
    data class ChangeEmail(val email: String) : LoginUiEvent()

    data class ChangePassword(val password: String) : LoginUiEvent()

    data class DismissUserMessage(val id: Int) : LoginUiEvent()

    data class TogglePassword(val isVisible: Boolean) : LoginUiEvent()

    data class GoogleSignIn(val signInResult: SignInResult) : LoginUiEvent()

    data class ToggleRememberMe(val remember: Boolean) : LoginUiEvent()

    data object SubmitData : LoginUiEvent()

    data object ResetState : LoginUiEvent()
}
