package com.githukudenis.comlib.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.common.ResponseResult
import com.githukudenis.comlib.core.data.repository.UserPrefsRepository
import com.githukudenis.comlib.core.data.repository.UserRepository
import com.githukudenis.comlib.core.model.user.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class Profile(
    val firstname: String? = null,
    val lastname: String? = null,
    val email: String? = null,
    val imageUrl: String? = null
)

sealed interface ProfileItemState {
    data object Loading : ProfileItemState

    data class Success(val profile: Profile) : ProfileItemState

    data class Error(val message: String) : ProfileItemState
}

fun User.toProfile(): Profile {
    return Profile(firstname = firstname, lastname = lastname, email = email, imageUrl = image)
}

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val userPrefsRepository: UserPrefsRepository, private val userRepository: UserRepository
) : ViewModel() {

    val state: StateFlow<SettingsUiState> = userPrefsRepository.userPrefs.mapLatest { prefs ->
            prefs.userId?.let { id ->
                val profile = when (val result = userRepository.getUserById(id)) {
                    is ResponseResult.Failure -> ProfileItemState.Error(result.error.message)
                    is ResponseResult.Success -> ProfileItemState.Success(result.data.data.user.toProfile())
                }
                SettingsUiState(profileItemState = profile)
            } ?: SettingsUiState()
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SettingsUiState()
        )
}