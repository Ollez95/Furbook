package com.example.home.ui.pet_buddies.pet_add_post

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.core.domain.home.petbuddies.model.Tag
import com.example.navigation.navigateBack


@Composable
fun PetAddPostScreen(navController: NavController, viewModel: PetAddPostViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect {
            when (it) {
                PetAddPostEvent.NavigateBack, PetAddPostEvent.OnAddPostSuccess -> {
                    navController.navigateBack()
                }
            }
        }
    }


    PetAddPostContent(state = state, onEvent = viewModel::onEvent)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun PetAddPostContent(
    state: PetBuddiesPostState = PetBuddiesPostState(),
    onEvent: (PetAddPostEvent) -> Unit,
) {
    val animalTypes = listOf("Dog", "Cat", "Bird", "Rabbit", "Other")
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent())
    { uri -> onEvent(PetAddPostEvent.OnImageSelected(uri ?: Uri.EMPTY)) }

    ShowError(state, onEvent)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Post Pet", style = MaterialTheme.typography.headlineMedium) },
                navigationIcon = {
                    IconButton(onClick = { onEvent(PetAddPostEvent.NavigateBack) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                // Animal Type Dropdown
                ExposedDropdownMenuBox(
                    expanded = state.isExpanded,
                    onExpandedChange = { onEvent(PetAddPostEvent.OnExpandClicked(it)) }
                ) {
                    OutlinedTextField(
                        value = state.selectedAnimal ?: "Select Animal Type",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(state.isExpanded) },
                        modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable)
                    )
                    ExposedDropdownMenu(
                        expanded = state.isExpanded,
                        onDismissRequest = { onEvent(PetAddPostEvent.OnExpandClicked(false)) }
                    ) {
                        animalTypes.forEach { animal ->
                            DropdownMenuItem(
                                text = { Text(animal) },
                                onClick = {
                                    onEvent(PetAddPostEvent.OnAnimalSelected(animal))
                                    onEvent(PetAddPostEvent.OnExpandClicked(false))
                                }
                            )
                        }
                    }
                }
            }

            item {
                // Description Input
                OutlinedTextField(
                    value = state.description,
                    onValueChange = { onEvent(PetAddPostEvent.OnDescriptionChanged(it)) },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false,
                    maxLines = 4
                )
            }

            item {
                // Image Picker
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clickable { launcher.launch("image/*") }
                        .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (state.imageUri != null) {
                        AsyncImage(
                            model = state.imageUri,
                            contentDescription = "Selected Image",
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Text("Tap to select an image", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            item {
                // User-Added Tag Input
                OutlinedTextField(
                    value = state.newTag.tag,
                    onValueChange = { onEvent(PetAddPostEvent.OnNewTagChanged(Tag(it))) },
                    label = { Text("Add New Tag") },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                if (state.newTag.tag.isNotBlank() && state.newTag !in state.tags) {
                                    onEvent(PetAddPostEvent.OnTagChanged(state.newTag))
                                    onEvent(PetAddPostEvent.OnNewTagChanged(Tag("")))
                                }
                            }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Add Tag")
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }

            item {
                // Tags Selection
                Text("Select Tags", style = MaterialTheme.typography.bodyLarge)
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    state.tags.forEach { tag ->
                        FilterChip(
                            selected = state.selectedTags.contains(tag),
                            onClick = {
                                onEvent(PetAddPostEvent.OnTagSelected(tag))
                            },
                            label = { Text(tag.tag) },
                            leadingIcon = if (state.selectedTags.contains(tag)) {
                                { Icon(Icons.Default.Check, contentDescription = null) }
                            } else {
                                null
                            }
                        )
                    }
                }
            }

            item {
                // Submit Button
                Button(
                    onClick = {
                        onEvent(PetAddPostEvent.OnAddPostClicked(state.imageUri ?: Uri.EMPTY))
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = state.description.isNotEmpty() &&
                            state.imageUri != null &&
                            state.selectedAnimal != null
                ) {
                    Text("Post")
                }
            }

            item {
                if (state.isLoading) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}

@Composable
private fun ShowError(state: PetBuddiesPostState, onEvent: (PetAddPostEvent) -> Unit) {
    val context = LocalContext.current
    state.errorMessage?.let { message ->
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        onEvent(PetAddPostEvent.OnPostErrorReset)
    }
}


