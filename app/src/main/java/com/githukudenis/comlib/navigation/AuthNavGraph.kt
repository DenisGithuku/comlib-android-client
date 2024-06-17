
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
package com.githukudenis.comlib.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.material3.SnackbarHostState
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.githukudenis.comlib.feature.auth.presentation.login.LoginRoute
import com.githukudenis.comlib.feature.auth.presentation.reset.ResetPasswordRoute
import com.githukudenis.comlib.feature.auth.presentation.signup.SignUpRoute

fun NavGraphBuilder.authGraph(
    snackbarHostState: SnackbarHostState,
    onSignUpInstead: () -> Unit,
    onLoginComplete: () -> Unit,
    onSignUpComplete: () -> Unit,
    onResetComplete: () -> Unit,
    onForgotPassword: () -> Unit,
    onSignInInstead: () -> Unit
) {
    navigation(
        startDestination = AuthDestination.Login.route,
        route = ComlibDestination.AuthGraph.route
    ) {
        composable(
            route = AuthDestination.Login.route,
            enterTransition = {
                scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 500))
            },
            exitTransition = { scaleOut(targetScale = 0.8f, animationSpec = tween(durationMillis = 300)) }
        ) {
            LoginRoute(
                onLoginComplete = onLoginComplete,
                onForgotPassword = onForgotPassword,
                onSignUpInstead = onSignUpInstead
            )
        }
        composable(
            route = AuthDestination.SignUp.route,
            enterTransition = {
                scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 500))
            },
            exitTransition = { scaleOut(targetScale = 0.8f, animationSpec = tween(durationMillis = 300)) }
        ) {
            SignUpRoute(onSignUpComplete = onSignUpComplete, onSignInInstead = onSignInInstead)
        }
        composable(
            route = AuthDestination.ForgotPassword.route,
            enterTransition = {
                scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 500))
            },
            exitTransition = { scaleOut(targetScale = 0.8f, animationSpec = tween(durationMillis = 300)) }
        ) {
            ResetPasswordRoute(snackbarHostState = snackbarHostState, onReset = onResetComplete)
        }
    }
}

sealed class AuthDestination(val route: String) {
    data object Login : AuthDestination("login")

    data object SignUp : AuthDestination("signup")

    data object ForgotPassword : AuthDestination("forgot_password")
}
