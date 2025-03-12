package com.example.home.ui.main

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
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
import com.example.home.navigation.bottomSheetGraph
import com.example.home.ui.composables.MainBottomNavigationBar
import com.example.home.ui.composables.MainDrawer
import com.example.home.ui.main.top_app_bar.MainTopNavigationBar
import com.example.navigation.AuthenticationNavigation
import com.example.navigation.HomeNavigation
import com.example.navigation.navigateToDestination
import com.example.navigation.navigateToDestinationCleaningStack
import com.example.ui.theme.FurbookTheme
import kotlinx.coroutines.flow.collectLatest

@Composable
fun MainScreen(appNavController: NavController,
               viewModel: MainViewModel = hiltViewModel()) {
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

                MainEvent.NavigateToPetAddPost -> appNavController.navigateToDestination(HomeNavigation.PetAddPost)
            }
        }
    }

    MainContent(drawerState, appNavController, state, viewModel::onEvent, )
}

@Composable
fun MainContent(
    drawerState: DrawerState,
    appNavController: NavController,
    state: MainState = MainState(),
    onEvent: (MainEvent) -> Unit = {},
    bottomSheetController: NavHostController = rememberNavController(),
) {
    MainDrawer(user = state.user, onEvent = onEvent, drawerState = drawerState) {
        Scaffold(
            topBar = { MainTopNavigationBar(drawerState, appNavController, state.screen) },
            bottomBar = { MainBottomNavigationBar(navController = bottomSheetController) }
        ) { innerPadding ->
            NavHost(
                navController = bottomSheetController,
                startDestination = HomeNavigation.Main,
                modifier = Modifier.padding(innerPadding)
            ) {
                bottomSheetGraph(onEvent)
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun MainContentDrawerClosedPreview() {
    FurbookTheme {
        val navHostController = rememberNavController()
        MainContent(drawerState = rememberDrawerState(DrawerValue.Closed), appNavController =  navHostController)
    }
}
