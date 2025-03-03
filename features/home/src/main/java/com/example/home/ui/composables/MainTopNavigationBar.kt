package com.example.home.ui.composables

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import com.example.home.ui.main.MainEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(onEvent: (MainEvent) -> Unit) {
    TopAppBar(
        title = { Text("Furbook", style = MaterialTheme.typography.headlineMedium) },
        navigationIcon = {
            IconButton(onClick = { onEvent(MainEvent.OpenCloseDrawer) }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        }
    )
}