
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

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryBooks
import androidx.compose.material.icons.outlined.People
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.githukudenis.comlib.app.AppState
import com.githukudenis.comlib.feature.books.BooksRoute
import com.githukudenis.comlib.feature.home.HomeRoute

fun NavGraphBuilder.homeNavGraph(
    appState: AppState,
    startDestination: String = HomeDestination.Home.route
) {
    navigation(startDestination = startDestination, route = ComlibDestination.HomeGraph.route) {
        composable(route = HomeDestination.Home.route) {
            HomeRoute(
                onOpenBookDetails = { bookId ->
                    appState.navigate(
                        route = "${ComlibDestination.BookDetail.route}/$bookId",
                        popUpTo = "${ComlibDestination.BookDetail.route}/$bookId"
                    )
                },
                onOpenAllBooks = {
                    appState.navigate(
                        route = HomeDestination.Books.route,
                        popUpTo = HomeDestination.Books.route
                    )
                },
                onOpenProfile = {
                    appState.navigate(
                        route = ComlibDestination.Profile.route,
                        popUpTo = ComlibDestination.Profile.route
                    )
                },
                onNavigateToStreakDetails = { bookId ->
                    appState.navigate(
                        route = "${ComlibDestination.Streak.route}/$bookId",
                        popUpTo = "${ComlibDestination.Streak.route}/$bookId"
                    )
                }
            )
        }
        composable(route = HomeDestination.Clubs.route) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Clubs")
            }
        }
        composable(route = HomeDestination.Books.route) {
            BooksRoute(
                onOpenBook = { bookId ->
                    appState.navigate(
                        route = "${ComlibDestination.BookDetail.route}/$bookId",
                        popUpTo = "${ComlibDestination.BookDetail.route}/$bookId"
                    )
                }
            )
        }
    }
}

sealed class HomeDestination(
    val route: String,
    val label: String? = null,
    val selectedIcon: ImageVector? = null,
    val unselectedIcon: ImageVector? = null
) {
    data object Home :
        HomeDestination(
            route = "home",
            label = "Home",
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home
        )

    data object Books :
        HomeDestination(
            route = "books",
            label = "Books",
            selectedIcon = Icons.Filled.LibraryBooks,
            unselectedIcon = Icons.Outlined.LibraryBooks
        )

    data object Clubs :
        HomeDestination(
            route = "clubs",
            label = "Clubs",
            selectedIcon = Icons.Filled.People,
            unselectedIcon = Icons.Outlined.People
        )
}
