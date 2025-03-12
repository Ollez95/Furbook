package com.example.home.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.core.domain.shared.model.User

@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeContent(state)
}

@Composable
fun HomeContent(state: HomeState) {
    val user = state.user
    val username = user?.username

    Column {
        Text("Hello, $username", style = MaterialTheme.typography.headlineLarge)
    }
}

@Preview
@Composable
private fun HomeContentPreview() {
    val user = User(username = "John Doe")
    HomeContent(HomeState(user = user))
}
