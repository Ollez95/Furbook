package com.example.home.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.core.domain.shared.model.User
import com.example.home.ui.main.MainEvent

@Composable
fun MainDrawer(
    modifier: Modifier = Modifier,
    user: User,
    drawerState: DrawerState,
    onEvent: (MainEvent) -> Unit = {},
    content: (@Composable () -> Unit))
{
    ModalNavigationDrawer(
        modifier = modifier,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.fillMaxWidth(0.7f)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                ) {
                    // **User Profile Picture**
                    Image(
                        painter =
                        // Fix this and use an image that the user can save in the database
                        painterResource(id = com.example.ui.R.drawable.on_boarding_image_1),
                        contentDescription = "User Profile",
                        modifier = Modifier
                            .size(80.dp)
                            .clip(CircleShape)

                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // **User Name**
                    Text(text = user.username, style = MaterialTheme.typography.headlineSmall)

                    // **User Email**
                    Text(
                        text = user.mail,
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
                        modifier = Modifier.align(Alignment.Start),
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
        content()
    }
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

@Preview
@Composable
private fun MainDrawerPreview() {
    //Create a Main Drawer
    MainDrawer(
        user = User(
            id = "1",
            username = "John Doe",
            mail = "asfasf"),
        drawerState = DrawerState(DrawerValue.Open)){
        Column { Text("asfasf") }
    }

}