package com.example.home.ui.composables

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.home.ui.main.MainEvent
import com.example.home.ui.main.MainScreenEnum

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopNavigationBar(
    screenEnum: MainScreenEnum,
    onEvent: (MainEvent) -> Unit) {
    TopAppBar(
        title = { Text(addTitle(screenEnum), style = MaterialTheme.typography.headlineMedium) },
        navigationIcon = {
            if(screenEnum == MainScreenEnum.HOME){
                IconButton(onClick = { onEvent(MainEvent.OpenCloseDrawer) }) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                }
            }
        },
        actions = {
            if(screenEnum == MainScreenEnum.PET_BUDDIES) {
                IconButton(onClick = {
                    onEvent(MainEvent.NavigateToPetAddPost)
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
    when(screenEnum){
        MainScreenEnum.HOME -> "Furbook"
        MainScreenEnum.PET_BUDDIES -> "Make Buddies"
        MainScreenEnum.INBOX -> "Check your inbox"
    }