package com.example.home.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import coil3.request.crossfade
import com.example.core.domain.home.petbuddies.model.AnimalPostModel
import com.example.core.domain.home.petbuddies.model.Tag

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PetPost(animalPostModel: AnimalPostModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = animalPostModel.username,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(animalPostModel.imageUrl)
                .crossfade(true)
                .listener(
                    onError = { _, throwable -> Log.e("CoilError",
                        "Failed to load image", throwable.throwable) }
                )
                .build(),
            contentDescription = "Pet image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(450.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display Tags
        if (animalPostModel.tags?.isNotEmpty() != false) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                animalPostModel.tags?.forEach { tag ->
                    AssistChip(
                        onClick = { /* No action needed, just for display */ },
                        label = { Text(tag.tag) }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = animalPostModel.description,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = animalPostModel.dateCreated,
            style = MaterialTheme.typography.bodySmall
        )

    }
}

@Preview
@Composable
private fun PetPostPreview() {
    PetPost(AnimalPostModel(
        username = "Dog",
        description = "This is a dog",
        tags = listOf(
            Tag("Friendly"),
            Tag("Playful"),
            Tag("Loyal")
        )
    ))
}