package com.example.home.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.domain.home.petbuddies.model.AnimalPostModel
import com.example.ui.R

@Composable
fun PetPost(animalPostModel: AnimalPostModel) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = animalPostModel.name, style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(8.dp))
        Image(
            painter = painterResource(id = R.drawable.on_boarding_image_1),
            contentDescription = "Pet image",
            modifier = Modifier
                .height(350.dp)
                .fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = animalPostModel.description, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview
@Composable
private fun PetPostPreview() {
    PetPost(AnimalPostModel(
        name = "Dog",
        description = "This is a dog"
    ))
}