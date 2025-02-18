package com.example.authentication.ui.register

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.authentication.ui.composables.PasswordWithoutIconOutlinedTextField
import com.example.ui.R
import com.example.ui.composables.WaveBackground
import com.example.ui.theme.FurbookTheme

@Composable
fun RegisterScreen(){
    RegisterContent(
        state = RegisterState(),
        onEvent = {}
    )
}

@Composable
fun RegisterContent(
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit = {},
) {
    Scaffold(
        snackbarHost = { }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // ✅ New Wave Background for Register Screen
                Box(modifier = Modifier.fillMaxWidth()) {
                    WaveBackground(color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f))
                    Image(
                        painter = painterResource(id = R.drawable.gmail),
                        contentDescription = "Register Logo",
                        modifier = Modifier
                            .size(80.dp)
                            .align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // ✅ Full Name Field
                OutlinedTextField(
                    value = "",
                    onValueChange = {  },
                    label = { Text("Full Name") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // ✅ Email Field
                OutlinedTextField(
                    value = "asf",
                    onValueChange = {  },
                    label = { Text("Email") },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp)
                )

                Spacer(modifier = Modifier.height(12.dp))

                // ✅ Password Field
                PasswordWithoutIconOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    label = "Password"
                )

                Spacer(modifier = Modifier.height(12.dp))

                // ✅ Confirm Password Field
                PasswordWithoutIconOutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    label = "Confirm Password"
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ✅ Register Button
                Button(
                    onClick = {  },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    shape = MaterialTheme.shapes.large // Rounded corners
                ) {
                    Text("Register")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ✅ Already have an account? Login
                TextButton(onClick = {  }) {
                    Text("Already have an account? Login", fontSize = 14.sp, color = MaterialTheme.colorScheme.primary)
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
private fun RegisterScreenPreview() {
    FurbookTheme {
        RegisterContent(
            state = RegisterState(),  // Example state
            onEvent = {}  // Empty handler for preview
        )
    }
}