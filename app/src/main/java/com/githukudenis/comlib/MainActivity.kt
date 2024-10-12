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
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.githukudenis.comlib.app.AppState
import com.githukudenis.comlib.app.rememberAppState
import com.githukudenis.comlib.core.designsystem.ui.theme.ComLibTheme
import com.githukudenis.comlib.core.model.ThemeConfig
import com.githukudenis.comlib.navigation.ComlibDestination
import com.githukudenis.comlib.navigation.ComlibNavGraph
import com.githukudenis.comlib.navigation.HomeDestination
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        val viewModel: MainActivityViewModel by viewModels<MainActivityViewModel>()

        var uiState: MainActivityUiState by mutableStateOf(MainActivityUiState())

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.onEach { uiState = it }.collect()
            }
        }

        WindowCompat.setDecorFitsSystemWindows(window, false)

        super.onCreate(savedInstanceState)

        setContent {
            val appState = rememberAppState()

            ComLibTheme(darkTheme = systemInDarkTheme(uiState)) {
                setSystemTheme(uiState = uiState)
                WindowCompat.getInsetsController(
                    window, window.decorView
                ).isAppearanceLightStatusBars = !systemInDarkTheme(uiState)

                Surface {
                    Scaffold(snackbarHost = { SnackbarHost(hostState = appState.snackbarHostState) },
                        bottomBar = {
                            ComlibBottomNavigationBar(appState)
                        }) {
                        ComlibNavGraph(
                            appState = appState, startDestination = when {
                                shouldHideOnBoarding(uiState) -> ComlibDestination.HomeGraph.route
                                else -> ComlibDestination.GetStarted.route
                            }, isSetupComplete = uiState.isSetupComplete
                        )
                    }
                }
            }
        }
    }

    private fun shouldHideOnBoarding(uiState: MainActivityUiState): Boolean {
        return when {
            uiState.isLoading -> false
            uiState.isLoggedIn -> true
            else -> false
        }
    }

    private fun systemInDarkTheme(uiState: MainActivityUiState): Boolean {
        return when {
            uiState.isLoading -> false
            uiState.themeConfig == ThemeConfig.DARK -> true
            else -> false
        }
    }

    private fun setSystemTheme(uiState: MainActivityUiState) {
        when {
            uiState.themeConfig == ThemeConfig.SYSTEM -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }
}


@Composable
fun ComlibBottomNavigationBar(
    appState: AppState
) {
    // Only show bottom bar on routes in home graph
    AnimatedVisibility(enter = slideInVertically(initialOffsetY = { fullHeight -> fullHeight } // Slide in from bottom
    ),
        exit = slideOutVertically(targetOffsetY = { fullHeight -> fullHeight } // Slide out downwards
        ),
        visible = appState.currentDestination?.route == HomeDestination.Home.route || appState.currentDestination?.route == HomeDestination.Books.route || appState.currentDestination?.route == HomeDestination.Groups.route || appState.currentDestination?.route == HomeDestination.Settings.route) {
        NavigationBar(containerColor = MaterialTheme.colorScheme.background) {
            val homeGraphDestinations = listOf(
                HomeDestination.Home,
                HomeDestination.Books,
                HomeDestination.Groups,
                HomeDestination.Settings
            )
            homeGraphDestinations.forEach { destination ->
                NavigationBarItem(onClick = {
                    appState.navigate(
                        destination.route, destination.route, inclusive = true
                    )
                }, selected = appState.currentDestination?.route == destination.route, icon = {
                    (if (destination.route == appState.currentDestination?.route) {
                        destination.selectedIcon
                    } else destination.unselectedIcon)?.let {
                        Icon(
                            painter = painterResource(it),
                            contentDescription = destination.label
                        )
                    }
                }, colors = NavigationBarItemDefaults.colors(
                    //                                                    indicatorColor =
                    // MaterialTheme.colorScheme.secondaryContainer.copy(0.4f)
                ), label = {
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

}