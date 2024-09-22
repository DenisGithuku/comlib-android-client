
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
package com.githukudenis.comlib

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
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
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
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
                    Scaffold(
                        snackbarHost = { SnackbarHost(hostState = appState.snackbarHostState) },
                        bottomBar = {
                            // Only show bottom bar on routes in home graph
                            if (
                                appState.currentDestination?.route == HomeDestination.Home.route ||
                                    appState.currentDestination?.route == HomeDestination.Books.route ||
                                    appState.currentDestination?.route == HomeDestination.Groups.route
                            ) {
                                NavigationBar(containerColor = MaterialTheme.colorScheme.background) {
                                    val homeGraphDestinations =
                                        listOf(HomeDestination.Home, HomeDestination.Books, HomeDestination.Groups)
                                    homeGraphDestinations.forEach { destination ->
                                        NavigationBarItem(
                                            onClick = {
                                                appState.navigate(destination.route, destination.route, inclusive = true)
                                            },
                                            selected = appState.currentDestination?.route == destination.route,
                                            icon = {
                                                (if (destination.route == appState.currentDestination?.route) {
                                                        destination.selectedIcon
                                                    } else destination.unselectedIcon)
                                                    ?.let { Icon(imageVector = it, contentDescription = null) }
                                            },
                                            colors =
                                                NavigationBarItemDefaults.colors(
                                                    //                                                    indicatorColor =
                                                    // MaterialTheme.colorScheme.secondaryContainer.copy(0.4f)
                                                ),
                                            label = {
                                                destination.label?.let {
                                                    Text(
                                                        text = it,
                                                        style = MaterialTheme.typography.labelMedium,
                                                        fontWeight = FontWeight.Medium
                                                    )
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    ) {
                        ComlibNavGraph(
                            appState = appState,
                            startDestination =
                                when {
                                    state.isLoggedIn && state.isSetup -> {
                                        ComlibDestination.HomeGraph.route
                                    }
                                    !state.isLoggedIn -> {
                                        ComlibDestination.GetStarted.route
                                    }
                                    else -> ComlibDestination.GenreSetup.route
                                }
                        )
                    }
                }
            }
        }
    }
}
