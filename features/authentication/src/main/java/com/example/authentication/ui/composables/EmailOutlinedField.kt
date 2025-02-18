package com.example.authentication.ui.composables

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun EmailOutlinedTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    leadingIcon: (@Composable () -> Unit)? = null,
    onChange: (String) -> Unit, // ✅ Use String instead of generic type
) {
    OutlinedTextField(
        value = value,
        onValueChange = { onChange(it) }, // ✅ Directly pass `String`
        label = { Text(label) },
        leadingIcon = leadingIcon,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
        singleLine = true,
        modifier = modifier
    )
}

