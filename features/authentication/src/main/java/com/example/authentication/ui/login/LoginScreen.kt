package com.example.authentication.ui.login

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.authentication.ui.composables.EmailOutlinedTextField
import com.example.authentication.ui.composables.PasswordOutlinedTextField
import com.example.authentication.ui.composables.RegisterText
import com.example.core.domain.authentication.login.models.LoginModel
import com.example.navigation.AuthenticationNavigation
import com.example.navigation.HomeNavigation
import com.example.navigation.Navigator
import com.example.ui.composables.Logo
import com.example.ui.composables.WaveBackground
import com.example.ui.theme.FurbookTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navigator: Navigator,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                LoginEvent.LoginSuccess -> navigator.navigateToDestinationCleaningStack(HomeNavigation.Main)
                is LoginEvent.LoginError -> snackBarHostState.showSnackbar(event.message)
                LoginEvent.NavigateToSignUp -> navigator.navigateWithSafety(AuthenticationNavigation.Register)
                LoginEvent.NavigateToForgotPassword -> navigator.navigateWithSafety(AuthenticationNavigation.RecoverPassword(state.loginModel.email))
            }
        }
    }

    LoginContent(state = state, snackBarHostState = snackBarHostState, onEvent = { event ->
        viewModel.onEvent(event)
    })
}

@Composable
private fun LoginContent(
    state: LoginState,
    snackBarHostState: SnackbarHostState,
    onEvent: (LoginEvent) -> Unit = {},
) {
    Scaffold(snackbarHost = {
        SnackbarHost(snackBarHostState)
    }) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ✅ Wave background
                Box(modifier = Modifier.fillMaxWidth()) {
                    WaveBackground() // Blue color
                    Logo(
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                val modifierTextField = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)

                // ✅ Email Field
                EmailOutlinedTextField(modifier = modifierTextField, value = state.loginModel.email, label = "Email", leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email, // Replace with an email icon
                        contentDescription = "Email Icon"
                    )
                }, onChange = { onEvent(LoginEvent.EmailChanged(it)) } // ✅ Explicitly wrap in event
                )

                Spacer(modifier = Modifier.height(12.dp))

                // ✅ Password Field
                PasswordOutlinedTextField(modifier = modifierTextField, state) { onEvent(it) }

                Spacer(modifier = Modifier.height(24.dp))

                // ✅ Login Button
                Button(
                    onClick = { onEvent(LoginEvent.Login) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                        ,
                    shape = MaterialTheme.shapes.large,
                    enabled = !state.isLoading
                ) {
                    Text("Login")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ✅ Forgot Password & Register Navigation
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TextButton(onClick = { onEvent(LoginEvent.NavigateToForgotPassword) }) {
                        Text("FORGOT PASSWORD ?", fontSize = 14.sp, color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // ✅ Fix alignment of "Don't have an account? Register"
                    RegisterText(navigateToRegister = { onEvent(LoginEvent.NavigateToSignUp) })
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ✅ Social Media Login
                Text("or", fontSize = 14.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = { /* Facebook */ }) {
                        Image(
                            painter = painterResource(id = com.example.ui.R.drawable.gmail), // Replace with Facebook icon
                            contentDescription = "Gmail", modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
            // ✅ Show Circular Progress in the center of the screen when loading
            if (state.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(), // Dim background when loading
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Preview(
    name = "Light Mode", showBackground = true
)
@Preview(
    name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES // Enables dark mode preview
)
@Preview
@Composable
private fun LoginScreenPreview() {
    FurbookTheme {
        LoginContent(state = LoginState(loginModel = LoginModel("test@example.com")),
            snackBarHostState = SnackbarHostState() , // Example state
            onEvent = {}  // Empty handler for preview
        )
    }
}