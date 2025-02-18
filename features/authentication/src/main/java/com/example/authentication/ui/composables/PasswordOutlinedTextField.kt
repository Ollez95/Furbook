package com.example.authentication.ui.composables

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.example.authentication.ui.login.LoginEvent
import com.example.authentication.ui.login.LoginState

@Composable
fun PasswordOutlinedTextField(modifier: Modifier = Modifier, state: LoginState, onEvent: (LoginEvent) -> Unit) {
    OutlinedTextField(
        value = state.loginModel.password,
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
        modifier = modifier
    )
}

@Composable
fun PasswordWithoutIconOutlinedTextField(
    value: String,
    modifier: Modifier = Modifier,
    label: String,
    onChange: (String) -> Unit,) {
    OutlinedTextField(
        value = value,
        onValueChange = { onChange(it) },
        label = { Text(label) },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
        singleLine = true,
        visualTransformation = PasswordVisualTransformation(),
        modifier = modifier
    )
}