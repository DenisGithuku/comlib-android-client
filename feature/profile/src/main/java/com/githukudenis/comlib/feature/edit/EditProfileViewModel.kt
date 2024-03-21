package com.githukudenis.comlib.feature.edit

import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.StatefulViewModel
import com.githukudenis.comlib.core.domain.usecases.GetUserPrefsUseCase
import com.githukudenis.comlib.core.domain.usecases.GetUserProfileUseCase
import com.githukudenis.comlib.core.domain.usecases.UpdateUserUseCase
import com.githukudenis.comlib.core.model.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class EditProfileUiState(
    val isLoading: Boolean = false,
    val firstname: String? = null,
    val lastname: String? = null,
    val profileUrl: String? = null,
    val isUpdating: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val updateUserUseCase: UpdateUserUseCase,
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val getUserPrefsUseCase: GetUserPrefsUseCase,
) : StatefulViewModel<EditProfileUiState>(EditProfileUiState()) {


    init {
        getUserDetails()
    }

    private fun getUserDetails() {
        viewModelScope.launch {
            val userId: String = checkNotNull(getUserPrefsUseCase().first().userId)
            update {
                copy(isLoading = true)
            }
            getUserProfileUseCase(userId).also { user ->
                val newState: EditProfileUiState = user?.let {
                    EditProfileUiState(
                        isLoading = false,
                        firstname = user.firstname,
                        lastname = user.lastname,
                        profileUrl = user.image,
                    )
                } ?: EditProfileUiState(
                    isLoading = false, error = "Couldn't fetch profile. Please try again!"
                )
                update {
                    newState
                }
            }
        }
    }

    fun updateUser() {
        viewModelScope.launch {
            update { copy(isUpdating = true) }
            val user = User(
                firstname = state.value.firstname,
                lastname = state.value.lastname,
            )
            updateUserUseCase(
                user
            )
            update { copy(isUpdating = false) }
        }
    }

    fun onChangeFirstname(value: String) {
        update {
            copy(firstname = value)
        }
    }

    fun onChangeLastname(value: String) {
        update {
            copy(lastname = value)
        }
    }
}