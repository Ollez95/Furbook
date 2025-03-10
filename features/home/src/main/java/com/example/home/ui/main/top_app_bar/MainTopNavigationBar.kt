package com.example.home.ui.main.top_app_bar

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.home.ui.main.MainScreenEnum
import com.example.navigation.HomeNavigation
import com.example.navigation.navigateToDestination


@Composable
fun MainTopNavigationBar(
    drawerState: DrawerState,
    appNavController: NavController,
    screenEnum: MainScreenEnum,
    viewModel: MainTopNavigationBarViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                MainTopNavigationBarEvent.OpenCloseDrawer -> {
                    if (drawerState.isClosed) drawerState.open() else drawerState.close()
                }

                MainTopNavigationBarEvent.NavigateToPetAddPost -> appNavController.navigateToDestination(HomeNavigation.PetAddPost)
            }
        }
    }
    TopNavigationBarContent(screenEnum, viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBarContent(
    screenEnum: MainScreenEnum,
    onEvent: (MainTopNavigationBarEvent) -> Unit,
) {
    TopAppBar(
        title = { Text(addTitle(screenEnum), style = MaterialTheme.typography.headlineMedium) },
        navigationIcon = {
            if (screenEnum == MainScreenEnum.HOME) {
                IconButton(onClick = { onEvent(MainTopNavigationBarEvent.OpenCloseDrawer) }) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }
            }
        },
        actions = {
            if (screenEnum == MainScreenEnum.PET_BUDDIES) {
                IconButton(onClick = {
                    onEvent(MainTopNavigationBarEvent.NavigateToPetAddPost)
                }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        modifier = Modifier.size(32.dp),
                        contentDescription = "Localized description"
                    )
                }
            }
        }
    )
}

private fun addTitle(screenEnum: MainScreenEnum) =
    when (screenEnum) {
        MainScreenEnum.HOME -> "Furbook"
        MainScreenEnum.PET_BUDDIES -> "Make Buddies"
        MainScreenEnum.INBOX -> "Check your inbox"
    }