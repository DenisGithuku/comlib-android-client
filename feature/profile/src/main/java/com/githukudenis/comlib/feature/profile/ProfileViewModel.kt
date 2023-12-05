package com.githukudenis.comlib.feature.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.domain.usecases.ComlibUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val comlibUseCases: ComlibUseCases
) : ViewModel() {

    var uiState: MutableStateFlow<ProfileUiState> = MutableStateFlow(ProfileUiState())
        private set

    init {
        comlibUseCases.getUserPrefsUseCase()
            .distinctUntilChanged()
            .mapLatest { prefs ->
                getProfileDetails(prefs.userId ?: return@mapLatest)
            }
    }

    private fun getProfileDetails(userId: String) {
        viewModelScope.launch {
            uiState.update {
                ProfileUiState(isLoading = true)
            }
            val user = comlibUseCases.getUserProfileUseCase(userId)
            user?.toProfile()?.run {
                uiState.update {
                    ProfileUiState(
                        profile = this, isLoading = false
                    )
                }
            }
        }
    }
}