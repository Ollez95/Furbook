package com.example.home.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.core.domain.home.petbuddies.model.AnimalPostModel
import com.example.home.ui.pet_buddies.pet_posts.PetBuddiesEvent
import com.example.home.ui.pet_buddies.pet_posts.PetBuddiesState

@Composable
fun PetPostList(state: PetBuddiesState, onEvent: (PetBuddiesEvent) -> Unit = {}) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
    ) {
        item {
            AnimalFilter(state.animalFilters, onEvent) // Keeps selection UI inside LazyColumn
        }

        itemsIndexed(items = fakePetPosts, key = { _, pet -> pet.id }) { index, pet ->
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                PetPost(pet)
                if (index < fakePetPosts.lastIndex) { // Add divider except for last item
                    HorizontalDivider(
                        color = Color.Gray.copy(alpha = 0.3f),
                        thickness = 1.dp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}


val fakePetPosts = listOf(
    AnimalPostModel(id = "0", name = "Bella", description = "A friendly golden retriever who loves to play in the park."),
    AnimalPostModel(id = "1", name = "Milo", description ="A curious tabby cat who enjoys sunbathing by the window."),
    AnimalPostModel(id = "2", name = "Coco", description ="A playful parrot that loves to mimic human speech."),
    AnimalPostModel(id = "3", name = "Buddy", description ="An energetic Labrador that enjoys outdoor adventures."),
    AnimalPostModel(id = "4", name = "Luna", description ="A fluffy rabbit that loves munching on carrots."),
    AnimalPostModel(id = "5", name = "Rocky", description ="A tiny hamster that loves running on his wheel.")
)