package com.example.home.ui.main

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.home.navigation.homeGraph
import com.example.home.ui.composables.MainBottomNavigationBar
import com.example.home.ui.composables.MainDrawer
import com.example.home.ui.composables.TopNavigationBar
import com.example.navigation.AuthenticationNavigation
import com.example.navigation.HomeNavigation
import com.example.navigation.navigateToDestinationCleaningStack
import com.example.ui.theme.FurbookTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel(), appNavController: NavController) {

    val state by viewModel.state.collectAsStateWithLifecycle()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    BackHandler(enabled = drawerState.isOpen) {
        viewModel.onEvent(MainEvent.OpenCloseDrawer)
    }

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                MainEvent.LogoutSuccess -> appNavController
                    .navigateToDestinationCleaningStack(
                        navigationToClean = HomeNavigation.Main,
                        navigateToDestination = AuthenticationNavigation.Login,
                        useTheSameInstance = false
                    )

                MainEvent.OpenCloseDrawer -> {
                    if (drawerState.isClosed) {
                        drawerState.open()
                    } else {
                        drawerState.close()
                    }
                }
            }
        }
    }

    MainContent(drawerState, state, viewModel::onEvent)
}

@Composable
fun MainContent(
    drawerState: DrawerState,
    state: MainState = MainState(),
    onEvent: (MainEvent) -> Unit = {},
    navController: NavHostController = rememberNavController(),
) {
    MainDrawer(user = state.user, onEvent = onEvent, drawerState = drawerState) {
        Scaffold(
            topBar = { TopNavigationBar(onEvent) },
            bottomBar = { MainBottomNavigationBar(navController = navController) }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = HomeNavigation.Main,
                modifier = Modifier.padding(innerPadding)
            ) {
                homeGraph()
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainContentDrawerClosedPreview() {
    FurbookTheme {
        MainContent(drawerState = rememberDrawerState(DrawerValue.Closed))
    }
}
