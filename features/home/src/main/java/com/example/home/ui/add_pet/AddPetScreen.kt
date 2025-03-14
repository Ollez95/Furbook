package com.example.home.ui.add_pet

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.outlined.Pets
import androidx.compose.material.icons.rounded.Female
import androidx.compose.material.icons.rounded.Male
import androidx.compose.material.icons.rounded.QuestionMark
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil3.compose.AsyncImage
import com.example.core.domain.home.add_pet.model.Gender
import com.example.core.domain.home.add_pet.model.PetType
import com.example.home.ui.add_pet.AddPetEvent.OnMicroChipChanged
import com.example.navigation.navigateBack
import com.example.ui.R
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetScreen(
    navController: NavController,
    viewModel: AddPetViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                AddPetEvent.OnNavigateBack -> navController.navigateBack()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Pet") },
                navigationIcon = {
                    IconButton(onClick = { viewModel.onEvent(AddPetEvent.OnNavigateBack) }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        AddPetContent(
            modifier = Modifier.padding(paddingValues),
            state = state,
            viewModel::onEvent
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPetContent(
    modifier: Modifier = Modifier,
    state: AddPetState,
    onEvent: AddPetEvent.() -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val datePickerState = rememberDatePickerState()
    var showDatePicker by remember { mutableStateOf(false) }
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { onEvent(AddPetEvent.OnImageUriChanged(it)) }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        // Pet Image Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .clickable { imagePickerLauncher.launch("image/*") },
            contentAlignment = Alignment.Center
        ) {
            if (state.imageUri != null) {
                AsyncImage(
                    model = state.imageUri,
                    contentDescription = "Pet image",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.PhotoCamera,
                        contentDescription = "Add photo",
                        modifier = Modifier.size(40.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Add Photo",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Basic Info
        Text(
            text = "Basic Information",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Name
        OutlinedTextField(
            value = state.name,
            onValueChange = { onEvent(AddPetEvent.OnNameChanged(it)) },
            label = { Text("Pet Name") },
            isError = state.nameError != null,
            supportingText = state.nameError?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Spacer(modifier = Modifier.height(4.dp))

        PetTypeSelector(
            selectedType = state.type,
            onTypeSelected = { onEvent(AddPetEvent.OnTypeChanged(it)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Breed
        OutlinedTextField(
            value = state.breed,
            onValueChange = { onEvent(AddPetEvent.OnBreedChanged(it)) },
            label = { Text("Breed") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Birthdate
        OutlinedTextField(
            value = state.birthdate?.format(DateTimeFormatter.ofPattern("MMM dd, yyyy")) ?: "",
            onValueChange = {},
            label = { Text("Birthdate") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(Icons.Default.DateRange, contentDescription = "Select date")
                }
            },
            readOnly = true
        )

        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(
                        onClick = {
                            datePickerState.selectedDateMillis?.let {
                                onEvent(AddPetEvent.OnBirthdateChanged(
                                    Instant.ofEpochMilli(it)
                                        .atZone(ZoneId.systemDefault())
                                        .toLocalDate()
                                ))
                            }
                            showDatePicker = false
                        }
                    ) {
                        Text("Confirm")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        GenderSelector(
            selectedGender = state.gender,
            onGenderSelected = { onEvent(AddPetEvent.OnGenderChanged(it)) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Additional Info
        Text(
            text = "Additional Information",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Weight
        OutlinedTextField(
            value = state.weight.toString(),
            onValueChange = { onEvent(AddPetEvent.OnWeightChanged(it.toFloat())) },
            label = { Text("Weight (kg)") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Color
        OutlinedTextField(
            value = state.color,
            onValueChange = { onEvent(AddPetEvent.OnColorChanged(it)) },
            label = { Text("Color") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { focusManager.moveFocus(FocusDirection.Down) }
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Microchip number
        OutlinedTextField(
            value = state.microchipNumber,
            onValueChange = { onEvent(OnMicroChipChanged(it)) },
            label = { Text("Microchip Number (Optional)") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Submit button
        Button(
            onClick = { onEvent(AddPetEvent.OnSubmit(state)) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {
            if (state.isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Add Pet")
            }
        }

        if (state.error != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = state.error,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun PetTypeSelector(
    selectedType: PetType,
    onTypeSelected: (PetType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = "Pet Type",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            item {
                PetTypeCard(
                    type = PetType.DOG,
                    selected = selectedType == PetType.DOG,
                    onSelect = { onTypeSelected(PetType.DOG) }
                )
            }
            item {
                PetTypeCard(
                    type = PetType.CAT,
                    selected = selectedType == PetType.CAT,
                    onSelect = { onTypeSelected(PetType.CAT) }
                )
            }
            item {
                PetTypeCard(
                    type = PetType.BIRD,
                    selected = selectedType == PetType.BIRD,
                    onSelect = { onTypeSelected(PetType.BIRD) }
                )
            }
            item {
                PetTypeCard(
                    type = PetType.OTHER,
                    selected = selectedType == PetType.OTHER,
                    onSelect = { onTypeSelected(PetType.OTHER) }
                )
            }
        }
    }
}

@Composable
fun PetTypeCard(
    type: PetType,
    selected: Boolean,
    onSelect: () -> Unit,
) {
    Card(
        modifier = Modifier
            .size(width = 120.dp, height = 160.dp)
            .clickable(onClick = onSelect),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (selected) 8.dp else 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (selected)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surface
        ),
        border = if (selected)
            BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        else
            null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(
                        if (selected)
                            MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    ),
                contentAlignment = Alignment.Center
            ) {
                when (type) {
                    PetType.DOG -> {
                        Image(
                            painter = painterResource(id = R.drawable.dog_filter),
                            contentDescription = "Dog",
                            modifier = Modifier.size(60.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    PetType.CAT -> {
                        Image(
                            painter = painterResource(id = R.drawable.cat_filter),
                            contentDescription = "Cat",
                            modifier = Modifier.size(60.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    PetType.BIRD -> {
                        Image(
                            painter = painterResource(id = R.drawable.bird_filter),
                            contentDescription = "Bird",
                            modifier = Modifier.size(60.dp),
                            contentScale = ContentScale.Fit
                        )
                    }

                    PetType.OTHER -> {
                        Icon(
                            imageVector = Icons.Outlined.Pets,
                            contentDescription = "Other Pet",
                            modifier = Modifier.size(60.dp),
                            tint = if (selected)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = when (type) {
                    PetType.DOG -> "Dog"
                    PetType.CAT -> "Cat"
                    PetType.BIRD -> "Bird"
                    PetType.OTHER -> "Other"
                },
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                color = if (selected)
                    MaterialTheme.colorScheme.onPrimaryContainer
                else
                    MaterialTheme.colorScheme.onSurface
            )

            if (selected) {
                Spacer(modifier = Modifier.height(4.dp))
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun GenderSelector(
    selectedGender: Gender,
    onGenderSelected: (Gender) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        Text(
            text = "Gender",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            GenderCard(
                gender = Gender.MALE,
                selected = selectedGender == Gender.MALE,
                onSelect = { onGenderSelected(Gender.MALE) },
                modifier = Modifier.weight(1f)
            )

            GenderCard(
                gender = Gender.FEMALE,
                selected = selectedGender == Gender.FEMALE,
                onSelect = { onGenderSelected(Gender.FEMALE) },
                modifier = Modifier.weight(1f)
            )

            GenderCard(
                gender = Gender.UNKNOWN,
                selected = selectedGender == Gender.UNKNOWN,
                onSelect = { onGenderSelected(Gender.UNKNOWN) },
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun GenderCard(
    gender: Gender,
    selected: Boolean,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backgroundColor = if (selected) {
        when (gender) {
            Gender.MALE -> Color(0xFFB3E5FC).copy(alpha = 0.7f)
            Gender.FEMALE -> Color(0xFFF8BBD0).copy(alpha = 0.7f)
            Gender.UNKNOWN -> MaterialTheme.colorScheme.surfaceVariant
        }
    } else {
        MaterialTheme.colorScheme.surface
    }

    val borderColor = if (selected) {
        when (gender) {
            Gender.MALE -> Color(0xFF03A9F4)
            Gender.FEMALE -> Color(0xFFE91E63)
            Gender.UNKNOWN -> MaterialTheme.colorScheme.primary
        }
    } else {
        Color.Transparent
    }

    val textColor = if (selected) {
        when (gender) {
            Gender.MALE -> Color(0xFF01579B)
            Gender.FEMALE -> Color(0xFFC2185B)
            Gender.UNKNOWN -> MaterialTheme.colorScheme.onSurfaceVariant
        }
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Card(
        modifier = modifier
            .height(110.dp)
            .clickable(onClick = onSelect),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (selected) 4.dp else 1.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = backgroundColor
        ),
        border = BorderStroke(
            width = if (selected) 2.dp else 0.dp,
            color = borderColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            when (gender) {
                Gender.MALE -> {
                    Icon(
                        imageVector = Icons.Rounded.Male,
                        contentDescription = "Male",
                        modifier = Modifier.size(40.dp),
                        tint = textColor
                    )
                }

                Gender.FEMALE -> {
                    Icon(
                        imageVector = Icons.Rounded.Female,
                        contentDescription = "Female",
                        modifier = Modifier.size(40.dp),
                        tint = textColor
                    )
                }

                Gender.UNKNOWN -> {
                    Icon(
                        imageVector = Icons.Rounded.QuestionMark,
                        contentDescription = "Unknown",
                        modifier = Modifier.size(40.dp),
                        tint = textColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = when (gender) {
                    Gender.MALE -> "Male"
                    Gender.FEMALE -> "Female"
                    Gender.UNKNOWN -> "Unknown"
                },
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                color = textColor
            )

            if (selected) {
                Spacer(modifier = Modifier.height(4.dp))
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected",
                    tint = textColor,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun AddPetScreenPreview() {
    AddPetScreen(rememberNavController())
}