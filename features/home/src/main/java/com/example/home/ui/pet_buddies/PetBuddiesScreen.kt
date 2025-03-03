package com.example.home.ui.pet_buddies

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.home.ui.composables.PetPostList
import kotlinx.coroutines.delay
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
fun PetBuddiesContent(state: PetBuddiesState, onEvent: (PetBuddiesEvent) -> Unit) {

    val pullRefreshState = rememberPullToRefreshState()
    var isRefreshing by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    PullToRefreshBox(state = pullRefreshState, isRefreshing = isRefreshing, onRefresh = {
        coroutineScope.launch {
            isRefreshing = true
            delay(2000)
            isRefreshing = false
        }
    }) {
        PetPostList(state, onEvent)
    }
}

@Preview
@Composable
private fun PetBuddiesScreenPreview() {
    PetBuddiesScreen()
}