package com.example.home.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.domain.home.petbuddies.model.AnimalEnum
import com.example.core.domain.home.petbuddies.model.listAnimals
import com.example.home.ui.pet_buddies.pet_posts.PetBuddiesEvent

@Composable
fun AnimalFilter(
    selectedAnimals: Set<AnimalEnum>,
    onEvent: (PetBuddiesEvent) -> Unit = {},
) {
    val animals = listAnimals // Using the predefined list

    AnimalFilterRow(animals, selectedAnimals) { animal ->
        val newSelection = if (selectedAnimals.contains(animal)) {
            selectedAnimals - animal // Deselect if already selected
        } else {
            selectedAnimals + animal // Select if not selected
        }
        onEvent(PetBuddiesEvent.OnAnimalFilterClicked(newSelection))
    }
}

@Composable
fun AnimalFilterRow(
    animals: List<AnimalEnum>,
    selectedAnimals: Set<AnimalEnum>, // Store selected items as Enums
    onAnimalSelected: (AnimalEnum) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(animals) { animal ->
            val isSelected = selectedAnimals.contains(animal)
            AnimalFilterItem(animal, isSelected) {
                onAnimalSelected(animal)
            }
        }
    }
}

@Composable
fun AnimalFilterItem(
    animal: AnimalEnum,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable { onClick() }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                    shape = CircleShape
                )
                .background(if (isSelected) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f) else Color.Transparent)
        ) {
            Image(
                painter = painterResource(id = animal.imageUrl),
                contentDescription = stringResource(id = animal.animalName),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(56.dp)
                    .clip(CircleShape)
                    .alpha(if (isSelected) 0.6f else 1f) // Dim effect when selected
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = stringResource(animal.animalName),
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Preview
@Composable
private fun AnimalFilterDemoPreview() {
    AnimalFilter(emptySet())
}

