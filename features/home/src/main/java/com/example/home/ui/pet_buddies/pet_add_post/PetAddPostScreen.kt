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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.navigation.navigateBack


@Composable
fun PetAddPostScreen(navController: NavController, viewModel: PetAddPostViewModel = hiltViewModel()) {
    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect {
            when (it) {
                PetAddPostEvent.NavigateBack -> {
                    navController.navigateBack()
                }
            }
        }
    }

    PetAddPostContent(viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetAddPostContent(onEvent: (PetAddPostEvent) -> Unit) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Post Pet", style = MaterialTheme.typography.headlineMedium) },
            navigationIcon = {
                IconButton(onClick = {
                    onEvent(PetAddPostEvent.NavigateBack)
                }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            },
        )
    }) {
        Column(
            modifier = Modifier.padding(it)
        ) {
            Text("Post Pet Content", modifier = Modifier.padding(16.dp))
        }
    }
}
