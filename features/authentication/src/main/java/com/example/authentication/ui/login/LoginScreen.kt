package com.example.authentication.ui.login

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
fun LoginScreen(navigator: Navigator) {
    Scaffold { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Login Screen"
            )
            Button(onClick = { navigator.navigateToDestinationCleaningStack(AuthenticationNavigation.Register)
            }) {
                Text(text = "Navigate to Register")
            }
        }
    }
}