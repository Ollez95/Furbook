package com.example.home.ui.main

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.home.navigation.homeGraph
import com.example.navigation.AuthenticationNavigation
import com.example.navigation.HomeNavigation
import com.example.navigation.NavigationDestination
import com.example.navigation.Navigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.example.ui.theme.FurbookTheme

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    navigator: Navigator
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                MainEvent.LogoutSuccess -> navigator.navigateBackUntil(AuthenticationNavigation.Login)
            }
        }
    }
    MainContent(state, viewModel::onEvent)
}

@Composable
fun MainContent(
    state: MainState = MainState(),
    onEvent: (MainEvent) -> Unit = {},
    navController: NavHostController= rememberNavController())
{
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    val user = state.user

    // Simulated User Data (Replace with actual user data)
    val userName = user.username
    val userEmail = user.mail
    val userProfilePic = painterResource(id = com.example.ui.R.drawable.on_boarding_image_1) // Replace with actual image

    ModalNavigationDrawer(
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // **User Profile Picture**
                    Image(
                        painter = userProfilePic,
                        contentDescription = "User Profile",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // **User Name**
                    Text(text = userName, style = MaterialTheme.typography.headlineSmall)

                    // **User Email**
                    Text(
                        text = userEmail,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // **Navigation Options**
                    DrawerNavItem("Profile", Icons.Default.Person) { /* Navigate to Profile */ }
                    DrawerNavItem("Saved Posts", Icons.Default.Build) { /* Navigate to Saved */ }
                    DrawerNavItem("Notifications", Icons.Default.Notifications) { /* Navigate to Notifications */ }
                    DrawerNavItem("Settings", Icons.Default.Settings) { /* Navigate to Settings */ }

                    Spacer(modifier = Modifier.height(16.dp))

                    // **Logout Button**
                    TextButton(
                        onClick = { onEvent(MainEvent.Logout) },
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ExitToApp, contentDescription = "Logout")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Log Out")
                    }
                }
            }
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = { TopNavigationBar(coroutineScope, drawerState) },
            bottomBar = { BottomNavigationBar(navController) }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(coroutineScope: CoroutineScope, drawerState: DrawerState) {
    TopAppBar(
        title = { Text("Furbook", style = MaterialTheme.typography.headlineMedium) },
        navigationIcon = {
            IconButton(onClick = {
                coroutineScope.launch {
                    if (drawerState.isClosed) {
                        drawerState.open()
                    } else {
                        drawerState.close()
                    }
                }
            }) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        }
    )
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        val currentRoute = navController.currentDestination

        BottomNavItem.entries.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

enum class BottomNavItem(val route: NavigationDestination, val title: String, val icon: ImageVector) {
    Home(HomeNavigation.Main, "Home", Icons.Default.Home),
    Chat(HomeNavigation.Chat, "Chat", Icons.Default.Call),
    Inbox(HomeNavigation.Inbox, "Inbox", Icons.Default.Email)
}


@Composable
fun DrawerNavItem(title: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(50.dp)
            .clickable { onClick() }
    ) {
        Icon(icon, contentDescription = title, modifier = Modifier.padding(end = 16.dp))
        Text(title, fontSize = 18.sp)
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
private fun OnBoardingScreenPreview() {
    FurbookTheme {
        MainContent()
    }
}
