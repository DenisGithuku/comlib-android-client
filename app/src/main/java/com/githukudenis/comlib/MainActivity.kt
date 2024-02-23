package com.githukudenis.comlib

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.text.font.FontWeight
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.githukudenis.comlib.app.rememberAppState
import com.githukudenis.comlib.core.designsystem.ui.theme.ComLibTheme
import com.githukudenis.comlib.navigation.ComlibDestination
import com.githukudenis.comlib.navigation.ComlibNavGraph
import com.githukudenis.comlib.navigation.HomeDestination
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        val viewModel: MainActivityViewModel by viewModels<MainActivityViewModel>()

        // Enable support for SplashScreen API
        // for proper Android+ support
        installSplashScreen()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        super.onCreate(savedInstanceState)

        setContent {
            val appState = rememberAppState()
            val state by viewModel.state.collectAsStateWithLifecycle()

            ComLibTheme(darkTheme = false) {
                Surface {
                    Scaffold(snackbarHost = { SnackbarHost(hostState = appState.snackbarHostState) },
                        bottomBar = {
                            // Only show bottom bar on routes in home graph
                            if (appState.currentDestination?.route == HomeDestination.Home.route || appState.currentDestination?.route == HomeDestination.Books.route || appState.currentDestination?.route == HomeDestination.Clubs.route) {
                                NavigationBar(
                                    containerColor = MaterialTheme.colorScheme.background
                                ) {
                                    val homeGraphDestinations = listOf(
                                        HomeDestination.Home,
                                        HomeDestination.Books,
                                        HomeDestination.Clubs
                                    )
                                    homeGraphDestinations.forEach { destination ->
                                        NavigationBarItem(onClick = { appState.navigate(destination.route) },
                                            selected = appState.currentDestination?.route == destination.route,
                                            icon = {
                                                (if (destination.route == appState.currentDestination?.route) {
                                                    destination.selectedIcon
                                                } else destination.unselectedIcon)?.let {
                                                    Icon(
                                                        imageVector = it, contentDescription = null
                                                    )
                                                }
                                            },
                                            label = {
                                                destination.label?.let {
                                                    Text(
                                                        text = it,
                                                        style = MaterialTheme.typography.labelMedium,
                                                        fontWeight = FontWeight.Medium
                                                    )
                                                }
                                            })
                                    }
                                }
                            }
                        }) { paddingValues ->
                        ComlibNavGraph(
                            appState = appState,
                            startDestination = if (state.isLoggedIn) ComlibDestination.HomeGraph.route else ComlibDestination.GetStarted.route
                        )
                    }
                }
            }
        }
    }
}

