package com.githukudenis.comlib.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.githukudenis.comlib.feature.auth.presentation.login.LoginRoute

fun NavGraphBuilder.authGraph(
    onLoginComplete: () -> Unit
) {
        navigation(startDestination = AuthDestination.Login.route, route = ComlibDestination.Auth.route) {
            composable(route = AuthDestination.Login.route) {
                LoginRoute(onLoginComplete = onLoginComplete)
            }
    }
}

sealed class AuthDestination(val route: String) {
    data object Login: AuthDestination("login")
    data object SignUp: AuthDestination("login")
    data object ForgotPassword: AuthDestination("forgot_password")
}