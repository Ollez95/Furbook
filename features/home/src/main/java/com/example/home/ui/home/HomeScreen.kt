package com.example.home.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Brush
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Medication
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.core.domain.shared.model.User
import com.example.navigation.HomeNavigation
import com.example.navigation.navigateToDestination

@Composable
fun HomeScreen(appNavController: NavController, viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is HomeEvent.AddPetClicked -> appNavController.navigateToDestination(HomeNavigation.AddPet)
            }
        }
    }

    HomeContent(state, viewModel::onEvent)
}

@Composable
fun HomeContent(state: HomeState, onEvent: HomeEvent.() -> Unit = {}) {
    val user = state.user
    val username = user?.username ?: "Guest"
    val petCount = state.petCount ?: 0

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // User welcome section
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Hello, $username",
                    style = MaterialTheme.typography.headlineLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Welcome to your pet management dashboard",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "You have $petCount pet${if (petCount != 1) "s" else ""}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Button(onClick = { /* Add navigation to pet list */ }) {
                        Text("View Pets")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        ActionList(onEvent)

        Spacer(modifier = Modifier.height(24.dp))

        // Upcoming events section
        if (state.upcomingEvents.isNotEmpty()) {
            Text(
                text = "Upcoming Events",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn {
                items(items = state.upcomingEvents) { event ->
                    EventItem(event)
                }
            }
        }
    }
}

@Composable
fun ActionList(onEvent: HomeEvent.() -> Unit) {
    Text(
        text = "Quick Actions",
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(bottom = 12.dp)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(16.dp), // Increased from 8dp to 16dp
        verticalArrangement = Arrangement.spacedBy(16.dp),   // Increased from 8dp to 16dp
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp) // Added horizontal padding for the grid
    ) {
        item {
            ActionCard(
                title = "Add Pet",
                icon = Icons.Default.Add,
                onClick = {
                    onEvent(HomeEvent.AddPetClicked)
                }
            )
        }
        item {
            ActionCard(
                title = "Vet Visits",
                icon = Icons.Default.DateRange,
                onClick = { /* Navigate to vet visits */ }
            )
        }
        item {
            ActionCard(
                title = "Medications",
                icon = Icons.Default.Medication,
                onClick = { /* Navigate to medications */ }
            )
        }
        item {
            ActionCard(
                title = "Pet Profile",
                icon = Icons.Default.Pets,
                onClick = { /* Navigate to pet profile */ }
            )
        }
        item {
            ActionCard(
                title = "Reminders",
                icon = Icons.Default.Notifications,
                onClick = { /* Navigate to reminders */ }
            )
        }
        item {
            ActionCard(
                title = "Health",
                icon = Icons.Default.Favorite,
                onClick = { /* Navigate to health records */ }
            )
        }
    }
}


@Composable
fun ActionCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .size(120.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(36.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun EventItem(event: PetEvent) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = when (event.type) {
                    EventType.VET -> Icons.Default.MedicalServices
                    EventType.MEDICATION -> Icons.Default.Medication
                    EventType.GROOMING -> Icons.Default.Brush
                    else -> Icons.Default.Event
                },
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = event.petName,
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = event.date,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview
@Composable
private fun HomeContentPreview() {
    val user = User(username = "John Doe")
    HomeContent(HomeState(user = user))
}
