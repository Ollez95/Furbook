package com.example.home.ui.chat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ChatScreen() {
    Column {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Chat Content", modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun HomeScreen() {
    Column {
        Column(
            modifier = Modifier
                .padding()
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Home Content", modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun InboxScreen() {
    Column {
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Inbox Content", modifier = Modifier.padding(16.dp))
        }
    }
}