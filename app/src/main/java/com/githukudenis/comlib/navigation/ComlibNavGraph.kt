package com.githukudenis.comlib.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.githukudenis.comlib.BuildConfig
import com.githukudenis.comlib.app.AppState
import com.githukudenis.comlib.feature.add_book.AddBookRoute
import com.githukudenis.comlib.feature.book_detail.BookDetailRoute
import com.githukudenis.comlib.feature.genre_setup.GenreSetupScreen
import com.githukudenis.comlib.feature.my_books.MyBooksRoute
import com.githukudenis.comlib.feature.profile.ProfileRoute
import com.githukudenis.comlib.feature.streak.StreakScreen
import com.githukudenis.comlib.onboarding.OnBoardingScreen

@Composable
fun ComlibNavGraph(
    appState: AppState, startDestination: String
) {
    NavHost(navController = appState.navController, startDestination = startDestination) {
        authGraph(snackbarHostState = appState.snackbarHostState, onLoginComplete = {
            appState.navigate(
                ComlibDestination.HomeGraph.route, popUpTo = ComlibDestination.AuthGraph.route
            )
        }, onSignUpInstead = {
            appState.navigate(route = AuthDestination.SignUp.route)
        }, onResetComplete = {
            appState.navigate(route = AuthDestination.Login.route)
        }, onForgotPassword = {
            appState.navigate(route = AuthDestination.ForgotPassword.route)
        }, onSignUpComplete = {
            appState.navigate(route = AuthDestination.Login.route)
        }, onSignInInstead = {
            appState.popBackStack()
        })
        composable(enterTransition = {
            scaleIn(
                initialScale = 0.8f, animationSpec = tween(durationMillis = 500)
            )
        }, exitTransition = {
            scaleOut(
                targetScale = 0.8f, animationSpec = tween(durationMillis = 300)
            )
        }, route = ComlibDestination.GetStarted.route) {
            OnBoardingScreen {
                appState.navigate(
                    ComlibDestination.AuthGraph.route, popUpTo = ComlibDestination.GetStarted.route
                )
            }
        }
        homeNavGraph(appState = appState)
        composable(enterTransition = {
            scaleIn(
                initialScale = 0.8f, animationSpec = tween(durationMillis = 500)
            )
        }, exitTransition = {
            scaleOut(
                targetScale = 0.8f, animationSpec = tween(durationMillis = 300)
            )
        }, route = "${ComlibDestination.BookDetail.route}/{bookId}") {
            BookDetailRoute(onBackPressed = {
                appState.popBackStack()
            })
        }
        composable(enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right
            )
        }, exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left
            )
        }, route = ComlibDestination.Profile.route) {
            ProfileRoute(versionName = BuildConfig.VERSION_NAME, onBackPressed = {
                appState.popBackStack()
            }, onOpenMyBooks = {
                appState.navigate(
                    route = ComlibDestination.MyBooks.route,
                    popUpTo = ComlibDestination.MyBooks.route
                )
            }, onSignOut = {
                appState.navigate(route = ComlibDestination.AuthGraph.route)
            })
        }
        composable(enterTransition = {
            scaleIn(
                initialScale = 0.8f, animationSpec = tween(durationMillis = 500)
            )
        }, exitTransition = {
            scaleOut(
                targetScale = 0.8f, animationSpec = tween(durationMillis = 300)
            )
        }, route = ComlibDestination.MyBooks.route
        ) {
            MyBooksRoute(onNavigateToBookDetails = {},
                onNavigateToAddBook = { /*TODO*/ },
                onNavigateUp = {
                    appState.popBackStack()
                })
        }
        composable(route = ComlibDestination.AddBook.route, enterTransition = {
            scaleIn(
                initialScale = 0.8f, animationSpec = tween(durationMillis = 500)
            )
        }, exitTransition = {
            scaleOut(
                targetScale = 0.8f, animationSpec = tween(durationMillis = 300)
            )
        }) {
            AddBookRoute(onNavigateUp = {

            }, onBookAdded = {})
        }
        composable(route = ComlibDestination.GenreSetup.route, enterTransition = {
            scaleIn(
                initialScale = 0.8f, animationSpec = tween(durationMillis = 500)
            )
        }, exitTransition = {
            scaleOut(
                targetScale = 0.8f, animationSpec = tween(durationMillis = 300)
            )
        }) {
            GenreSetupScreen(onSkip = {
                appState.navigate(
                    route = ComlibDestination.HomeGraph.route,
                    popUpTo = ComlibDestination.GenreSetup.route
                )
            })
        }
        composable(route = "${ComlibDestination.Streak.route}/{bookId}", enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left
            )
        }, exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right
            )
        }) {
            StreakScreen(onNavigateUp = { appState.popBackStack() })
        }
    }
}

sealed class ComlibDestination(
    val route: String,
) {
    data object AuthGraph : ComlibDestination(route = "auth_graph")
    data object HomeGraph : ComlibDestination(
        route = "home_graph"
    )

    data object BookDetail : ComlibDestination(
        route = "book_detail"
    )

    data object GetStarted : ComlibDestination(route = "get_started")
    data object Profile : ComlibDestination(route = "profile")
    data object MyBooks : ComlibDestination(route = "my_books")
    data object AddBook : ComlibDestination(route = "add_book")
    data object GenreSetup : ComlibDestination(route = "genre_setup")
    data object Streak : ComlibDestination(route = "streak")
}