package com.githukudenis.comlib.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.githukudenis.comlib.app.AppState
import com.githukudenis.comlib.feature.home.HomeRoute
import com.githukudenis.comlib.onboarding.OnBoardingScreen

@Composable
fun ComlibNavGraph(
    appState: AppState, startDestination: String
) {
    NavHost(navController = appState.navController, startDestination = startDestination) {
        authGraph(
            snackbarHostState = appState.snackbarHostState,
            onLoginComplete = {
                appState.navigate(
                    ComlibDestination.HomeGraph.route,
                    popUpTo = ComlibDestination.AuthGraph.route
                )
            },
            onSignUpInstead = {
                appState.navigate(route = AuthDestination.SignUp.route)
            },
            onResetComplete = {
                appState.navigate(route = AuthDestination.Login.route)
            },
            onForgotPassword = {
                appState.navigate(route = AuthDestination.ForgotPassword.route)
            },
            onSignUpComplete = {
                appState.navigate(route = AuthDestination.Login.route)
            },

            )
        composable(route = ComlibDestination.GetStarted.route) {
            OnBoardingScreen {
                appState.navigate(
                    ComlibDestination.AuthGraph.route, popUpTo = ComlibDestination.GetStarted.route
                )
            }
        }
        homeNavGraph(appState = appState)
    }
}

sealed class ComlibDestination(
    val route: String,
) {
    data object AuthGraph : ComlibDestination(route = "auth_graph")
    data object HomeGraph : ComlibDestination(
        route = "home_graph"
    )

    data object GetStarted : ComlibDestination(route = "get_started")
}