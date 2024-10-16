
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

import androidx.core.util.PatternsCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.UserMessage
import com.githukudenis.comlib.core.data.repository.AuthRepository
import com.githukudenis.comlib.core.data.repository.UserPrefsRepository
import com.githukudenis.comlib.core.model.user.UserSignUpDTO
import com.githukudenis.comlib.feature.auth.presentation.common.PasswordRequirements
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class SignUpViewModel
@Inject
constructor(
    private val authRepository: AuthRepository,
    private val userPrefsRepository: UserPrefsRepository
) : ViewModel() {

    private val _state: MutableStateFlow<SignUpUiState> = MutableStateFlow(SignUpUiState())
    val state: StateFlow<SignUpUiState>
        get() = _state.asStateFlow()

    private val _showNetworkDialog = MutableStateFlow(false)
    val showNetworkDialog: StateFlow<Boolean>
        get() = _showNetworkDialog.asStateFlow()

    fun onEvent(event: SignUpUiEvent) {
        when (event) {
            is SignUpUiEvent.ChangeConfirmPassword -> {
                _state.update { prevState ->
                    val formState = prevState.formState.copy(confirmPassword = event.confirmPassword)
                    prevState.copy(formState = formState)
                }
            }
            is SignUpUiEvent.ChangeEmail -> {
                _state.update { prevState ->
                    val formState =
                        prevState.formState.copy(
                            email = event.email,
                            isEmailValid = PatternsCompat.EMAIL_ADDRESS.matcher(event.email).matches()
                        )
                    prevState.copy(formState = formState)
                }
            }
            is SignUpUiEvent.ChangeFirstname -> {
                _state.update { prevState ->
                    val formState = prevState.formState.copy(firstname = event.firstname)
                    prevState.copy(formState = formState)
                }
            }
            is SignUpUiEvent.ChangeLastname -> {
                _state.update { prevState ->
                    val formState = prevState.formState.copy(lastname = event.lastname)
                    prevState.copy(formState = formState)
                }
            }
            is SignUpUiEvent.ChangePassword -> {
                _state.update { prevState ->
                    val specialSymbolsRegex = "[!@#\$%^&*()_+\\-=\\[\\]{}|;:'\",.<>?]\n"
                    val requirements = mutableListOf<PasswordRequirements>()
                    if (event.password.length > 7) {
                        requirements.add(PasswordRequirements.Length)
                    }
                    if (event.password.any { it.isDigit() }) {
                        requirements.add(PasswordRequirements.Number)
                    }
                    if (event.password.any { it.isUpperCase() }) {
                        requirements.add(PasswordRequirements.CapitalLetter)
                    }
                    if (event.password.any { it in specialSymbolsRegex }) {
                        requirements.add(PasswordRequirements.SpecialCharacter)
                    }
                    val formState =
                        prevState.formState.copy(password = event.password, requirements = requirements)
                    prevState.copy(formState = formState)
                }
            }
            SignUpUiEvent.Submit -> {
                signUp()
            }
            is SignUpUiEvent.ToggleConfirmPasswordVisibility -> {
                _state.update { prevState ->
                    val formState = prevState.formState.copy(confirmPasswordIsVisible = event.isVisible)
                    prevState.copy(formState = formState)
                }
            }
            is SignUpUiEvent.GoogleSignIn -> {
                //                onSignInResult(event.signInResult)
            }
            is SignUpUiEvent.TogglePasswordVisibility -> {
                _state.update { prevState ->
                    val formState = prevState.formState.copy(passwordIsVisible = event.isVisible)
                    prevState.copy(formState = formState)
                }
            }
            is SignUpUiEvent.DismissUserMessage -> {
                val userMessages = _state.value.userMessages.filterNot { message -> message.id == event.id }
                _state.update { prevState -> prevState.copy(userMessages = userMessages) }
            }
            is SignUpUiEvent.ToggleTerms -> {
                _state.update { prevState ->
                    val formState = prevState.formState.copy(acceptedTerms = event.accepted)
                    prevState.copy(formState = formState)
                }
            }
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            _state.update { prevState -> prevState.copy(isLoading = true) }
            val response =
                authRepository.signUp(
                    UserSignUpDTO(
                        firstname = _state.value.formState.firstname.trim(),
                        lastname = _state.value.formState.lastname.trim(),
                        email = _state.value.formState.email.trim(),
                        password = _state.value.formState.password.trim(),
                        passwordConfirm = _state.value.formState.confirmPassword.trim()
                    )
                )
            when (response) {
                is ResponseResult.Failure -> {
                    _state.update { prevState ->
                        val userMessages = prevState.userMessages.toMutableList()
                        userMessages.add(UserMessage(message = response.error.message))
                        prevState.copy(isLoading = false, userMessages = userMessages)
                    }
                }
                is ResponseResult.Success -> {
                    val userProfileData = userPrefsRepository.userPrefs.first().userProfileData
                    userPrefsRepository.setToken(response.data.token)
                    userPrefsRepository.setUserId(response.data.id)
                    userPrefsRepository.setUserProfileData(
                        userProfileData.copy(
                            email = _state.value.formState.email.trim(),
                            firstname = _state.value.formState.firstname.trim(),
                            lastname = _state.value.formState.lastname.trim()
                        )
                    )
                    _state.update { prevState -> prevState.copy(isLoading = false, signUpSuccess = true) }
                }
            }
        }
    }

    fun onDismissNetworkDialog() {
        _showNetworkDialog.update { false }
    }
}
