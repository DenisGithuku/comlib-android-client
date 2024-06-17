
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
package com.githukudenis.comlib

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.githukudenis.comlib.core.domain.usecases.GetUserPrefsUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MainActivityViewModel
@Inject
constructor(firebaseAuth: FirebaseAuth, private val getUserPrefsUseCase: GetUserPrefsUseCase) :
    ViewModel() {
    private val _state: MutableStateFlow<MainActivityUiState> =
        MutableStateFlow(MainActivityUiState())
    val state: StateFlow<MainActivityUiState>
        get() = _state.asStateFlow()

    init {
        if (firebaseAuth.currentUser != null) {
            _state.update { it.copy(isLoggedIn = true) }
        }
        getSetupState()
    }

    private fun getSetupState() {
        viewModelScope.launch {
            getUserPrefsUseCase().collectLatest { userPrefs ->
                _state.update { it.copy(isSetup = userPrefs.isSetup) }
            }
        }
    }
}

data class MainActivityUiState(val isLoggedIn: Boolean = false, val isSetup: Boolean = false)
