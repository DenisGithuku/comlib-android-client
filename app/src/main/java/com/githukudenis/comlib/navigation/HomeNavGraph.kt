package com.githukudenis.comlib.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.People
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.githukudenis.comlib.app.AppState
import com.githukudenis.comlib.feature.home.HomeRoute
import com.githukudenis.comlib.feature.home.HomeViewModel


fun NavGraphBuilder.homeNavGraph(
    appState: AppState,
    startDestination: String = HomeDestination.Home.route,
) {
    navigation(startDestination = startDestination, route = ComlibDestination.HomeGraph.route) {
        composable(route = HomeDestination.Home.route) {
            HomeRoute()
        }
        composable(route = HomeDestination.Books.route) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Books"
                )
            }
        }
        composable(route = HomeDestination.Clubs.route) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "Clubs"
                )
            }
        }
    }
}


sealed class HomeDestination(
    val route: String,
    val label: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
) {
    data object Home : HomeDestination(
        route = "home",
        label = "Home",
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
    )

    data object Books : HomeDestination(
        route = "books",
        label = "Books",
        selectedIcon = Icons.Filled.LibraryBooks,
        unselectedIcon = Icons.Outlined.LibraryBooks,
    )

    data object Clubs : HomeDestination(
        route = "clubs",
        label = "Clubs",
        selectedIcon = Icons.Filled.People,
        unselectedIcon = Icons.Outlined.People,
    )

}