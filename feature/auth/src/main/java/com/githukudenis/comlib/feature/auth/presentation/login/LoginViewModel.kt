package com.githukudenis.comlib.feature.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.MessageType
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.common.UserMessage
import com.githukudenis.comlib.core.model.user.User
import com.githukudenis.comlib.data.repository.AuthRepository
import com.githukudenis.comlib.data.repository.UserPrefsRepository
import com.githukudenis.comlib.data.repository.UserRepository
import com.githukudenis.comlib.feature.auth.presentation.SignInResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val userPrefsRepository: UserPrefsRepository,
) : ViewModel() {

    private var _state: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState>
        get() = _state.asStateFlow()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.ChangeEmail -> {
                changeEmail(event.email)
            }

            is LoginUiEvent.ChangePassword -> {
                changePassword(event.password)
            }

            is LoginUiEvent.GoogleSignIn -> {
                onSignInResult(event.signInResult)
            }

            is LoginUiEvent.DismissUserMessage -> {
                dismissUserMessage(event.id)
            }

            LoginUiEvent.SubmitData -> {
                login()
            }

            is LoginUiEvent.TogglePassword -> {
                togglePassword(event.isVisible)
            }

            is LoginUiEvent.ToggleRememberMe -> {
                toggleRememberMe(event.remember)
            }

            LoginUiEvent.ResetState -> {
                resetState()
            }
        }
    }

    private fun toggleRememberMe(value: Boolean) {
        _state.update { prevState ->
            val formState = prevState.formState.copy(rememberMe = value)
            prevState.copy(
                formState = formState
            )
        }
    }

    private fun changeEmail(value: String) {
        _state.update { prevState ->
            val formState = prevState.formState.copy(email = value)
            prevState.copy(
                formState = formState
            )
        }
    }

    private fun changePassword(value: String) {
        _state.update { prevState ->
            val formState = prevState.formState.copy(password = value)
            prevState.copy(
                formState = formState
            )
        }
    }

    private fun dismissUserMessage(id: Int) {
        val messages = _state.value.userMessages.filterNot { it.id == id }
        _state.update { prevState ->
            prevState.copy(userMessages = messages)
        }
    }

    private fun togglePassword(isVisible: Boolean) {
        _state.update { prevState ->
            val formState = prevState.formState.copy(
                passwordIsVisible = isVisible
            )
            prevState.copy(formState = formState)
        }
    }

    private fun onSignInResult(signInResult: SignInResult) {
        viewModelScope.launch {
            _state.update { prevState ->
                prevState.copy(isLoading = true)
            }
            if (signInResult.errorMessage != null) {
                _state.update { prevState ->
                    val userMessages = prevState.userMessages.toMutableList()
                    userMessages.add(UserMessage(message = signInResult.errorMessage))
                    prevState.copy(
                        isLoading = false, loginSuccess = true, userMessages = userMessages
                    )
                }
                return@launch
            }
            val user = signInResult.userData?.run {
                User(
                    email = email, username = username, image = profilePictureUrl, authId = authId
                )
            }
            val result = userRepository.addNewUser(
                user = user ?: return@launch
            )
            when (result) {
                is ResponseResult.Failure -> Unit
                is ResponseResult.Success -> {
                    user.authId?.let { userPrefsRepository.setUserId(it) }
                }
            }

            _state.update { prevState ->
                val userMessages = prevState.userMessages.toMutableList()
                userMessages.add(UserMessage(message = "Signed in successfully"))
                prevState.copy(
                    isLoading = false, loginSuccess = true, userMessages = userMessages
                )
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            val (email, password) = _state.value.formState
            if (email.isEmpty() || password.isEmpty()) {
                _state.update { prevState ->
                    val userMessages = prevState.userMessages.toMutableList()
                    userMessages.add(
                        UserMessage(
                            message = "Please check your details", messageType = MessageType.ERROR
                        )
                    )
                    prevState.copy(
                        userMessages = userMessages
                    )
                }
                return@launch
            }

            _state.update { prevState ->
                prevState.copy(isLoading = true)
            }


            authRepository.login(email, password, onSuccess = { authId ->
                userPrefsRepository.setUserId(authId)
                _state.update { prevState ->
                    prevState.copy(
                        isLoading = false, loginSuccess = true
                    )
                }
            }, onError = { error ->
                if (error != null) {
                    val userMessages = _state.value.userMessages.toMutableList()
                    userMessages.add(UserMessage(message = error.message))
                    _state.update { prevState ->
                        prevState.copy(
                            isLoading = false, loginSuccess = false, userMessages = userMessages
                        )
                    }
                }
            })

        }
    }

    private fun resetState() {
        _state.update {
            LoginUiState()
        }
    }
}

