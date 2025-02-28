package com.example.authentication.ui.recover_password

import android.content.res.Configuration
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.authentication.ui.composables.EmailOutlinedTextField
import com.example.navigation.AuthenticationNavigation
import com.example.navigation.navigateBack
import com.example.navigation.navigateWithSafety
import com.example.ui.composables.WaveBackground
import com.example.ui.theme.FurbookTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RecoverPasswordScreen(
    viewModel: RecoverPasswordViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                RecoverPasswordEvent.NavigateToLogin -> navController.navigateBack()
                RecoverPasswordEvent.RecoverPasswordSuccess -> navController.navigateWithSafety(AuthenticationNavigation.Login)
                is RecoverPasswordEvent.RecoverPasswordError -> snackBarHostState.showSnackbar(event.message)
            }
        }
    }

    RecoverPasswordContent(state = state, snackBarHostState = snackBarHostState, onEvent = { event ->
        viewModel.onEvent(event)
    })
}

@Composable
private fun RecoverPasswordContent(
    state: RecoverPasswordState,
    snackBarHostState: SnackbarHostState,
    onEvent: (RecoverPasswordEvent) -> Unit = {},
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
                    WaveBackground()
                }

                Spacer(modifier = Modifier.height(24.dp))

                val modifierTextField = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)

                // ✅ Email Field
                EmailOutlinedTextField(modifier = modifierTextField, value = state.email, label = "Email", leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Email, contentDescription = "Email Icon"
                    )
                }, onChange = { onEvent(RecoverPasswordEvent.EmailChanged(it)) } // ✅ Explicitly wrap in event
                )

                Spacer(modifier = Modifier.height(24.dp))

                // ✅ Submit Button
                Button(
                    onClick = { onEvent(RecoverPasswordEvent.RecoverPassword) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp),
                    shape = MaterialTheme.shapes.large,
                    enabled = !state.isLoading
                ) {
                    Text("Reset Password")
                }

                Spacer(modifier = Modifier.height(16.dp))

                // ✅ Go Back to Login
                TextButton(onClick = { onEvent(RecoverPasswordEvent.NavigateToLogin) }) {
                    Text("Back to Login", fontSize = 14.sp, color = Color.Gray)
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
private fun RecoverPasswordContentPreview() {
    FurbookTheme {
        RecoverPasswordContent(state = RecoverPasswordState(),
            snackBarHostState = SnackbarHostState(),
            onEvent = {}  // Empty handler for preview
        )
    }
}
