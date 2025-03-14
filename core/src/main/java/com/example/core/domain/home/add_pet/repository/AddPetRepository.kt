package com.example.core.domain.home.add_pet.repository

import com.example.core.domain.home.add_pet.model.PetModel
import com.example.core.utils.Response

interface AddPetRepository {
    suspend fun getPet(): Response<PetModel>
    suspend fun savePet(pet: PetModel): Response<PetModel>
}