package com.githukudenis.comlib.feature.auth.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.UserMessage
import com.githukudenis.comlib.core.model.User
import com.githukudenis.comlib.core.model.UserAuthData
import com.githukudenis.comlib.data.repository.AuthRepository
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
    private val userRepository: UserRepository
) : ViewModel() {

    private var _state: MutableStateFlow<LoginUiState> = MutableStateFlow(LoginUiState())
    val state: StateFlow<LoginUiState>
        get() = _state.asStateFlow()

    fun onEvent(event: LoginUiEvent) {
        when (event) {
            is LoginUiEvent.ChangeAge -> {
                changeAge(event.age)
            }

            is LoginUiEvent.ChangeEmail -> {
                changeEmail(event.email)
            }

            is LoginUiEvent.ChangeFirstname -> {
                changeFirstname(event.firstname)
            }

            is LoginUiEvent.ChangeLastname -> {
                changeLastname(event.lastname)
            }

            is LoginUiEvent.ChangePassword -> {
                changePassword(event.password)
            }

            is LoginUiEvent.ChangeConfirmPassword -> {
                changeConfirmPassword(event.confirm)
            }

            is LoginUiEvent.SignIn -> {
                onSignInResult(event.signInResult)
            }

            is LoginUiEvent.DismissUserMessage -> {
                dismissUserMessage(event.id)
            }

            LoginUiEvent.SubmitData -> {
                login()
            }

            is LoginUiEvent.TogglePassword -> {
                _state.update { prevState ->
                    val formState = prevState.formState.copy(
                        passwordIsVisible = event.isVisible
                    )
                    prevState.copy(formState = formState)
                }
            }
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

    private fun changeFirstname(value: String) {
        _state.update { prevState ->
            val formState = prevState.formState.copy(firstname = value)
            prevState.copy(
                formState = formState
            )
        }
    }

    private fun changeLastname(value: String) {
        _state.update { prevState ->
            val formState = prevState.formState.copy(lastname = value)
            prevState.copy(
                formState = formState
            )
        }
    }

    private fun changeAge(value: Int) {
        _state.update { prevState ->
            val formState = prevState.formState.copy(age = value)
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

    private fun changeConfirmPassword(value: String) {
        _state.update { prevState ->
            val formState = prevState.formState.copy(confirmPassword = value)
            prevState.copy(
                formState = formState
            )
        }
    }

    private fun onSignInResult(signInResult: SignInResult) {
        viewModelScope.launch {
            _state.update { prevState ->
                prevState.copy(isLoading = true)
            }
            val user = signInResult.userData?.run {
                User(
                    email = email,
                    username = username,
                    image = profilePictureUrl
                )
            }
            userRepository.addNewUser(
                user = user ?: return@launch
            )
            _state.update { prevState ->
                val userMessages = prevState.userMessages.toMutableList()
                userMessages.add(UserMessage(message = signInResult.errorMessage))
                prevState.copy(
                    isLoading = false,
                    loginSuccess = true,
                    userMessages = userMessages
                )
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            val (email, firstname, lastname, age, password, confirm) = _state.value.formState
            if (email.isEmpty() ||
                firstname.isEmpty() ||
                lastname.isEmpty() ||
                age == null ||
                password.isEmpty() ||
                confirm.isEmpty()
            ) {
                _state.update { prevState ->
                    val userMessages = prevState.userMessages.toMutableList()
                    userMessages.add(UserMessage(message = "Please check your details"))
                    prevState.copy(
                        userMessages = userMessages
                    )
                }
                return@launch
            }

            val userAuthData = UserAuthData(email, firstname, lastname, age, password)
            _state.update { prevState ->
                prevState.copy(isLoading = true)
            }
            val result = authRepository.login(userAuthData)
            _state.update { prevState ->
                prevState.copy(isLoading = false, loginSuccess = result != null)
            }
        }
    }

    private fun resetState() {
        _state.update {
            LoginUiState()
        }
    }
}

