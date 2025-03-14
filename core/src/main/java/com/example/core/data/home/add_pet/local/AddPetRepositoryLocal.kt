package com.example.core.data.home.add_pet.local

import com.example.core.domain.home.add_pet.model.PetModel
import com.example.core.domain.home.add_pet.repository.AddPetRepository
import com.example.core.utils.Response
import javax.inject.Inject

class AddPetRepositoryLocal @Inject constructor(

): AddPetRepository {
    override suspend fun getPet(): Response<PetModel> {
        TODO("Not yet implemented")
    }

    override suspend fun savePet(pet: PetModel): Response<PetModel> {
        TODO("Not yet implemented")
    }
}