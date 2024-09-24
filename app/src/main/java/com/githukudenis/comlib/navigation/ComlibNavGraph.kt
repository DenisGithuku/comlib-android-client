
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

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.githukudenis.comlib.BuildConfig
import com.githukudenis.comlib.app.AppState
import com.githukudenis.comlib.feature.add_book.AddBookRoute
import com.githukudenis.comlib.feature.book_detail.BookDetailRoute
import com.githukudenis.comlib.feature.edit.EditProfileScreen
import com.githukudenis.comlib.feature.genre_setup.GenreSetupScreen
import com.githukudenis.comlib.feature.my_books.MyBooksRoute
import com.githukudenis.comlib.feature.profile.ProfileRoute
import com.githukudenis.comlib.feature.streak.StreakScreen
import com.githukudenis.comlib.onboarding.OnBoardingScreen
import com.githukudenis.comlib.splashScreen
import com.githukudenis.comlib.splashScreenRoute

@Composable
fun ComlibNavGraph(appState: AppState, startDestination: String) {
    NavHost(navController = appState.navController, startDestination = splashScreenRoute) {
        splashScreen(onTimeout = { appState.navigate(startDestination, splashScreenRoute) })
        authGraph(
            snackbarHostState = appState.snackbarHostState,
            onLoginComplete = {
                appState.navigate(
                    ComlibDestination.HomeGraph.route,
                    popUpTo = ComlibDestination.AuthGraph.route,
                    inclusive = true
                )
            },
            onSignUpInstead = {
                appState.navigate(
                    route = AuthDestination.SignUp.route,
                    popUpTo = AuthDestination.SignUp.route,
                    inclusive = true
                )
            },
            onResetComplete = { appState.navigate(route = AuthDestination.Login.route) },
            onForgotPassword = { appState.navigate(route = AuthDestination.ForgotPassword.route) },
            onSignUpComplete = { appState.navigate(route = AuthDestination.Login.route) },
            onSignInInstead = { appState.popBackStack() }
        )
        composable(
            enterTransition = {
                scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                scaleOut(targetScale = 0.8f, animationSpec = tween(durationMillis = 300))
            },
            route = ComlibDestination.GetStarted.route
        ) {
            OnBoardingScreen {
                appState.navigate(
                    ComlibDestination.AuthGraph.route,
                    popUpTo = ComlibDestination.GetStarted.route,
                    inclusive = true
                )
            }
        }
        homeNavGraph(appState = appState)
        composable(
            enterTransition = {
                scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                scaleOut(targetScale = 0.8f, animationSpec = tween(durationMillis = 300))
            },
            route = "${ComlibDestination.BookDetail.route}/{bookId}"
        ) {
            BookDetailRoute(onBackPressed = { appState.popBackStack() })
        }
        composable(
            enterTransition = {
                slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right)
            },
            exitTransition = {
                slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            route = ComlibDestination.Profile.route
        ) {
            ProfileRoute(
                versionName = BuildConfig.VERSION_NAME,
                onBackPressed = { appState.popBackStack() },
                onOpenMyBooks = {
                    appState.navigate(
                        route = ComlibDestination.MyBooks.route,
                        popUpTo = ComlibDestination.MyBooks.route,
                        inclusive = true
                    )
                },
                onSignOut = {
                    appState.navigate(
                        route = ComlibDestination.AuthGraph.route,
                        popUpTo = ComlibDestination.HomeGraph.route,
                        inclusive = true
                    )
                },
                onEditProfile = {
                    appState.navigate(
                        route = ComlibDestination.EditProfile.route,
                        popUpTo = ComlibDestination.EditProfile.route,
                        inclusive = true
                    )
                }
            )
        }
        composable(
            enterTransition = {
                scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 500))
            },
            exitTransition = {
                scaleOut(targetScale = 0.8f, animationSpec = tween(durationMillis = 300))
            },
            route = ComlibDestination.MyBooks.route
        ) {
            MyBooksRoute(
                onNavigateToBookDetails = { bookId ->
                    appState.navigate(
                        route = "${ComlibDestination.BookDetail.route}/$bookId",
                        popUpTo = ComlibDestination.MyBooks.route,
                        inclusive = true
                    )
                },
                onNavigateToAddBook = {
                    appState.navigate(
                        route = ComlibDestination.AddBook.route,
                        popUpTo = ComlibDestination.AddBook.route,
                        inclusive = true
                    )
                },
                onNavigateUp = { appState.popBackStack() }
            )
        }
        composable(
            route = ComlibDestination.AddBook.route,
            enterTransition = {
                scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 500))
            },
            exitTransition = { scaleOut(targetScale = 0.8f, animationSpec = tween(durationMillis = 300)) }
        ) {
            AddBookRoute(
                onNavigateUp = { appState.popBackStack() },
                onBookAdded = { appState.popBackStack() }
            )
        }
        composable(
            route = ComlibDestination.GenreSetup.route,
            enterTransition = {
                scaleIn(initialScale = 0.8f, animationSpec = tween(durationMillis = 500))
            },
            exitTransition = { scaleOut(targetScale = 0.8f, animationSpec = tween(durationMillis = 300)) }
        ) {
            GenreSetupScreen(
                onSkip = {
                    appState.navigate(
                        route = ComlibDestination.HomeGraph.route,
                        popUpTo = ComlibDestination.GenreSetup.route,
                        inclusive = true
                    )
                }
            )
        }
        composable(
            route = "${ComlibDestination.Streak.route}/{bookId}",
            arguments =
                listOf(
                    navArgument("bookId") {
                        nullable = true
                        defaultValue = null
                    }
                ),
            enterTransition = {
                slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left)
            },
            exitTransition = {
                slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right)
            }
        ) {
            StreakScreen(onNavigateUp = { appState.popBackStack() })
        }
        composable(route = ComlibDestination.EditProfile.route) {
            EditProfileScreen(onNavigateUp = { appState.popBackStack() })
        }
    }
}

sealed class ComlibDestination(val route: String) {
    data object AuthGraph : ComlibDestination(route = "auth_graph")

    data object HomeGraph : ComlibDestination(route = "home_graph")

    data object BookDetail : ComlibDestination(route = "book_detail")

    data object GetStarted : ComlibDestination(route = "get_started")

    data object Profile : ComlibDestination(route = "profile")

    data object EditProfile : ComlibDestination(route = "edit_profile")

    data object MyBooks : ComlibDestination(route = "my_books")

    data object AddBook : ComlibDestination(route = "add_book")

    data object GenreSetup : ComlibDestination(route = "genre_setup")

    data object Streak : ComlibDestination(route = "streak")
}
