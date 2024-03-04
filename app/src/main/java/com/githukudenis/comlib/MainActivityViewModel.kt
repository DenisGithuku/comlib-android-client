package com.githukudenis.comlib

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.domain.usecases.ComlibUseCases
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    firebaseAuth: FirebaseAuth, private val comlibUseCases: ComlibUseCases
) : ViewModel() {
    private val _state: MutableStateFlow<MainActivityUiState> =
        MutableStateFlow(MainActivityUiState())
    val state: StateFlow<MainActivityUiState> get() = _state.asStateFlow()

    init {
        if (firebaseAuth.currentUser != null) {
            _state.update { it.copy(isLoggedIn = true) }
        }
        getSetupState()
    }

    private fun getSetupState() {
        viewModelScope.launch {
            comlibUseCases.getUserPrefsUseCase().collectLatest { userPrefs ->
                    _state.update { it.copy(isSetup = userPrefs.isSetup) }
                }
        }
    }
}

data class MainActivityUiState(
    val isLoggedIn: Boolean = false,
    val isSetup: Boolean = false,
)