package com.githukudenis.comlib.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.githukudenis.comlib.app.AppState
import com.githukudenis.comlib.onboarding.OnBoardingScreen

@Composable
fun ComlibNavGraph(
    appState: AppState,
    startDestination: String
) {
    NavHost(navController = appState.navController, startDestination = startDestination) {
        authGraph(onLoginComplete = {
            appState.navigate(ComlibDestination.Home.route, popUpTo = ComlibDestination.Auth.route)
        })
        composable(route = ComlibDestination.GetStarted.route) {
            OnBoardingScreen {
                appState.navigate(
                    ComlibDestination.Auth.route,
                    popUpTo = ComlibDestination.GetStarted.route
                )
            }
        }
    }
}

sealed class ComlibDestination(
    val route: String,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null
) {
    data object Auth : ComlibDestination(route = "auth")
    data object Home : ComlibDestination(
        route = "home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home
    )

    data object GetStarted : ComlibDestination(route = "get_started")
}