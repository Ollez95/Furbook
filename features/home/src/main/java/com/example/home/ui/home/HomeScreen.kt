package com.example.home.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    HomeContent(modifier)
}

@Composable
fun HomeContent(modifier: Modifier = Modifier) {
    Column {
        Text("Home Content")
    }
}

