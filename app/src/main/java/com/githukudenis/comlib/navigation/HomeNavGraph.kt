
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

import androidx.annotation.DrawableRes
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.githukudenis.comlib.R
import com.githukudenis.comlib.app.AppState
import com.githukudenis.comlib.core.designsystem.ui.theme.LocalDimens
import com.githukudenis.comlib.feature.books.BooksRoute
import com.githukudenis.comlib.feature.home.HomeRoute
import com.githukudenis.comlib.feature.settings.SettingsRoute

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
                        popUpTo = "${ComlibDestination.BookDetail.route}/$bookId",
                        inclusive = true
                    )
                },
                onOpenAllBooks = {
                    appState.navigate(
                        route = HomeDestination.Books.route,
                        popUpTo = HomeDestination.Books.route,
                        inclusive = true
                    )
                },
                onOpenProfile = {
                    appState.navigate(
                        route = ComlibDestination.Profile.route,
                        popUpTo = ComlibDestination.Profile.route,
                        inclusive = true
                    )
                },
                onNavigateToStreakDetails = { bookId ->
                    appState.navigate(
                        route = "${ComlibDestination.Streak.route}/$bookId",
                        popUpTo = "${ComlibDestination.Streak.route}/$bookId",
                        inclusive = true
                    )
                }
            )
        }
        composable(route = HomeDestination.Groups.route) {
            Box(
                modifier = Modifier.fillMaxSize().padding(LocalDimens.current.extraLarge),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.groups_status), textAlign = TextAlign.Center)
            }
        }
        composable(route = HomeDestination.Books.route) {
            BooksRoute(
                onOpenBook = { bookId ->
                    appState.navigate(
                        route = "${ComlibDestination.BookDetail.route}/$bookId",
                        popUpTo = "${ComlibDestination.BookDetail.route}/$bookId",
                        inclusive = true
                    )
                },
                onNavigateUp = { appState.popBackStack() }
            )
        }
        composable(
            enterTransition = {
                slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            exitTransition = {
                slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            route = HomeDestination.Settings.route
        ) {
            SettingsRoute(
                onNavigateUp = { appState.popBackStack() },
                onOpenEditProfile = {
                    appState.navigate(
                        route = ComlibDestination.Profile.route,
                        popUpTo = ComlibDestination.Profile.route,
                        inclusive = true
                    )
                }
            )
        }
    }
}

sealed class HomeDestination(
    val route: String,
    val label: String? = null,
    @DrawableRes val selectedIcon: Int? = null,
    @DrawableRes val unselectedIcon: Int? = null
) {
    data object Home :
        HomeDestination(
            route = "home",
            label = "Home",
            selectedIcon = R.drawable.home_filled,
            unselectedIcon = R.drawable.home_outlined
        )

    data object Books :
        HomeDestination(
            route = "books",
            label = "Books",
            selectedIcon = R.drawable.library_filled,
            unselectedIcon = R.drawable.library_outlined
        )

    data object Groups :
        HomeDestination(
            route = "groups",
            label = "Groups",
            selectedIcon = R.drawable.people_filled,
            unselectedIcon = R.drawable.people_outlined
        )

    data object Settings :
        HomeDestination(
            route = "settings",
            label = "Settings",
            selectedIcon = R.drawable.settings_filled,
            unselectedIcon = R.drawable.settings_outlined
        )
}
