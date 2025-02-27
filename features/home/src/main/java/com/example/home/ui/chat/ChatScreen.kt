package com.example.home.ui.chat

import android.provider.SyncStateContract.Columns
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
fun ChatScreen(innerPadding: PaddingValues) {
    Column {
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Chat Content", modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun MainScreen(innerPadding: PaddingValues) {
    Column {
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Main Content", modifier = Modifier.padding(16.dp))
        }
    }
}

@Composable
fun InboxScreen(innerPadding: PaddingValues) {
    Column {
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Inbox Content", modifier = Modifier.padding(16.dp))
        }
    }
}