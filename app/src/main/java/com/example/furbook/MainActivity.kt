package com.example.furbook

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.furbook.navigator.FurbookNavigation
import com.example.ui.theme.FurbookTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        // Initialize Splash Screen
        enableEdgeToEdge()
        setContent {
            FurbookTheme(dynamicColor = false) {
                FurbookNavigation()
            }
        }
    }
}
