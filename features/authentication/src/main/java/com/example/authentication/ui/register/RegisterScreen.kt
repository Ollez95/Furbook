package com.example.authentication.ui.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.navigation.AuthenticationNavigation
import com.example.navigation.Navigator

@Composable
fun RegisterScreen(navigator: Navigator) {
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Register Screen"
            )
            Button(
                onClick = { navigator.navigateToDestinationCleaningStack(AuthenticationNavigation.Login)
            }) {
                Text(text = "Navigate to Login")
            }
        }
    }
}