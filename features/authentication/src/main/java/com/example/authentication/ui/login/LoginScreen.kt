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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.authentication.ui.composables.RegisterText
import com.example.navigation.AuthenticationNavigation
import com.example.navigation.Navigator
import com.example.ui.composables.Logo
import com.example.ui.composables.WaveBackground
import com.example.ui.theme.FurbookTheme

@Composable
fun LoginScreen(viewModel: LoginViewModel = hiltViewModel(),
                navigator: Navigator) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                LoginEvent.LoginSuccess -> {
                    // Handle successful login navigation
                    navigator.navigateToDestinationCleaningStack(AuthenticationNavigation.Login)
                }
                is LoginEvent.Error -> {
                    // Show error message (e.g., using Snackbar)
                }
                LoginEvent.SignUp -> {
                    navigator.navigateToDestinationCleaningStack(AuthenticationNavigation.Register)
                }
                LoginEvent.ForgotPassword -> {
                    navigator.navigateToDestinationCleaningStack(AuthenticationNavigation.ForgotPassword(state.email))
                }
                // Handle other events if needed
                else -> {}
            }
        }
    }

    LoginContent(state = state, onEvent = { event ->
        viewModel.onEvent(event)
    })
}

@Composable
fun LoginContent(state: LoginState, onEvent: (LoginEvent) -> Unit = {}) {
    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
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

                // ✅ Email Field
                OutlinedTextField(
                    value = state.email,
                    onValueChange = { onEvent(LoginEvent.EmailChanged(it)) },
                    label = { Text("Email") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email, // Replace with an email icon
                            contentDescription = "Email Icon"
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // ✅ Password Field
                OutlinedTextField(
                    value = state.password,
                    onValueChange = { onEvent(LoginEvent.PasswordChanged(it)) },
                    label = { Text("Password") },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock, // Replace with a password icon
                            contentDescription = "Password Icon"
                        )
                    },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
                    singleLine = true,
                    visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { onEvent(LoginEvent.PasswordVisibilityChanged(!state.isPasswordVisible)) }) {
                            Icon(
                                imageVector = if (state.isPasswordVisible) Icons.Default.Visibility
                                else Icons.Default.VisibilityOff,
                                contentDescription = if (state.isPasswordVisible) "Hide password" else "Show password"
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ✅ Login Button
                Button(
                    onClick = { onEvent(LoginEvent.Login) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    shape = MaterialTheme.shapes.large // Rounded corners
                ) {
                    Text("Login")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ✅ Forgot Password & Register Navigation
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    TextButton(onClick = { onEvent(LoginEvent.ForgotPassword) }) {
                        Text("FORGOT PASSWORD ?", fontSize = 14.sp, color = Color.Gray)
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // ✅ Fix alignment of "Don't have an account? Register"
                    RegisterText(navigateToRegister = { onEvent(LoginEvent.SignUp) })
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ✅ Social Media Login
                Text("or", fontSize = 14.sp, color = Color.Gray)

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(onClick = { /* Facebook */ }) {
                        Image(
                            painter = painterResource(id = com.example.ui.R.drawable.gmail), // Replace with Facebook icon
                            contentDescription = "Gmail",
                            modifier = Modifier.size(32.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(
    name = "Light Mode",
    showBackground = true
)
@Preview(
    name = "Dark Mode",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES // Enables dark mode preview
)
@Preview
@Composable
private fun LoginScreenPreview() {
    FurbookTheme {
        LoginContent(
            state = LoginState(email = "test@example.com"),  // Example state
            onEvent = {}  // Empty handler for preview
        )
    }
}