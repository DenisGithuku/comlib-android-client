package com.githukudenis.comlib.app

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController()
): AppState = remember(navController) {
    AppState(
        navController = navController
    )
}

@Stable
data class AppState(
    val navController: NavHostController
) {
    val currentDestination: NavDestination?
        @Composable get() = navController.currentBackStackEntryAsState().value?.destination

    fun navigate(
        route: String,
        popUpTo: String? = null,
    ) {
        navController.navigate(route) {
            popUpTo(route = popUpTo ?: route) {
                inclusive = true
            }
        }
    }

    fun popBackStack() = navController.popBackStack()
}