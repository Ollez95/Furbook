package com.example.authentication.ui.composables

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import com.example.authentication.ui.login.LoginEvent
import com.example.authentication.ui.login.LoginState

@Composable
fun EmailOutlinedTextField(modifier: Modifier = Modifier, state: LoginState, onEvent: (LoginEvent) -> Unit) {
    OutlinedTextField(
        value = state.loginModel.email,
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
        modifier = modifier
    )
}

