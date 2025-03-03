package com.example.home.ui.pet_buddies.pet_add_post

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetAddPostScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post Pet", style = MaterialTheme.typography.headlineMedium) },
                navigationIcon = {
                    IconButton(onClick = {

                    })
                    {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        }
    ) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text("Post Pet Content", modifier = Modifier.padding(16.dp))
        }
    }
}