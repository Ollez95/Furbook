package com.example.home.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.domain.home.petbuddies.model.AnimalPostModel
import com.example.core.domain.home.petbuddies.model.Tag
import com.example.ui.R

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun PetPost(animalPostModel: AnimalPostModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = animalPostModel.name,
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        Image(
            painter = painterResource(id = R.drawable.on_boarding_image_1),
            contentDescription = "Pet image",
            modifier = Modifier
                .height(350.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = animalPostModel.description,
            style = MaterialTheme.typography.bodyLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Display Tags
        if (animalPostModel.tags.isNotEmpty()) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                animalPostModel.tags.forEach { tag ->
                    AssistChip(
                        onClick = { /* No action needed, just for display */ },
                        label = { Text(tag.tag) }
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun PetPostPreview() {
    PetPost(AnimalPostModel(
        name = "Dog",
        description = "This is a dog",
        tags = listOf(
            Tag("Friendly"),
            Tag("Playful"),
            Tag("Loyal")
        )
    ))
}