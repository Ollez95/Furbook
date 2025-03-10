package com.example.home.ui.pet_buddies.pet_posts

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.home.ui.composables.PetPostList
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@Composable
fun PetBuddiesScreen(viewModel: PetBuddiesViewModel = hiltViewModel()) {

    val state by viewModel.state.collectAsStateWithLifecycle()

    PetBuddiesContent(
        state = state,
        onEvent = { viewModel.onEvent(it) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PetBuddiesContent(state: PetBuddiesState = PetBuddiesState(), onEvent: (PetBuddiesEvent) -> Unit = {}) {
    val pullRefreshState = rememberPullToRefreshState()
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(Unit) {
        onDispose {
            coroutineScope.cancel()
        }
    }

    PullToRefreshBox(state = pullRefreshState, isRefreshing = state.isRefreshing, onRefresh = {
        coroutineScope.launch {
            onEvent(PetBuddiesEvent.OnRefresh)
        }
    }) {
        PetPostList(state, onEvent)
    }
}

@Preview
@Composable
private fun PetBuddiesScreenPreview() {
    PetBuddiesContent()
}