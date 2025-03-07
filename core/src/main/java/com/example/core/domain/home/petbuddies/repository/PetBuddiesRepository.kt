package com.example.core.domain.home.petbuddies.repository

import android.net.Uri
import com.example.core.domain.home.petbuddies.model.AnimalPostModel
import com.example.core.utils.Response

interface PetBuddiesRepository {
    suspend fun getAnimalPosts(): Response<List<AnimalPostModel>>
    suspend fun createAnimalPost(uri: Uri, animalPostModel: AnimalPostModel): Response<Boolean>
    suspend fun getAnimalPostFilteredByAnimals(animals: List<String>): Response<List<AnimalPostModel>>
}