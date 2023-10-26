package com.githukudenis.comlib

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.githukudenis.comlib.app.rememberAppState
import com.githukudenis.comlib.core.designsystem.ui.theme.ComLibTheme
import com.githukudenis.comlib.navigation.ComlibDestination
import com.githukudenis.comlib.navigation.ComlibNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {


        // Enable support for SplashScreen API
        // for proper Android+ support
        installSplashScreen()

        WindowCompat.setDecorFitsSystemWindows(window, false)

        super.onCreate(savedInstanceState)

        setContent {
            val appState = rememberAppState()
            ComLibTheme(darkTheme = false) {
               ComlibNavGraph(appState = appState, startDestination = ComlibDestination.GetStarted.route)
            }
        }
    }
}

