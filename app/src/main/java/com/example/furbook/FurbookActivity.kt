package com.example.furbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.furbook.navigator.FurbookNavigation
import com.example.ui.theme.FurbookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FurbookActivity : ComponentActivity() {

    private val viewModel: FurbookViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        // Initialize Splash Screen
        splashScreen.setKeepOnScreenCondition {
            viewModel.state.value.isLoading
        }

        enableEdgeToEdge()
        setContent {
            val state by viewModel.state.collectAsStateWithLifecycle()

            if (!state.isLoading) {
                FurbookTheme(dynamicColor = false) {
                    FurbookNavigation(state)
                }
            }
        }
    }
}
