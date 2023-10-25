package com.githukudenis.comlib.feature.auth.presentation.login

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
internal fun LoginRoute(
    loginViewModel: LoginViewModel = hiltViewModel(),
    onLoginComplete: () -> Unit
) {

}

@Composable
private fun LoginScreen(
    loginUiState: LoginUiState,
    onSubmit: () -> Unit
) {

    
}