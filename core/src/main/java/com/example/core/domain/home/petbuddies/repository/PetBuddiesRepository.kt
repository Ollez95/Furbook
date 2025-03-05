package com.example.core.domain.home.petbuddies.repository

import com.example.core.domain.home.petbuddies.model.AnimalPostModel
import com.example.core.utils.Response
import kotlinx.coroutines.flow.Flow

interface PetBuddiesRepository {
    suspend fun getAnimalPosts(): Response<List<AnimalPostModel>>
    fun createAnimalPost(animalPostModel: AnimalPostModel): Flow<Response<Boolean>>
    suspend fun getAnimalPostFilteredByAnimals(animals: List<String>): Response<List<AnimalPostModel>>
}