package com.example.home.ui.main

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.FurbookTheme
import kotlinx.coroutines.launch

@Composable
fun MainScreen() {
    MainContent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent() {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    // Simulated User Data (Replace with actual user data)
    val userName = "John Doe"
    val userEmail = "johndoe@example.com"
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
                        onClick = { /* Perform Logout Action */ }
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
            topBar = {
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
        ) { innerPadding ->
            Column(
                modifier = Modifier.padding(innerPadding).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Main Content", modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun DrawerNavItem(title: String, icon: androidx.compose.ui.graphics.vector.ImageVector, onClick: () -> Unit) {
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
