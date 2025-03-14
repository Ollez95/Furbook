package com.example.core.data.home.add_pet.remote

import com.example.core.data.home.petbuddies.remote.PetBuddiesRepositoryRemote
import com.example.core.data.home.petbuddies.remote.PetBuddiesRepositoryRemote.Companion.ANIMAL_POSTS
import com.example.core.data.shared.supabase.repositories.SupabaseStorageUtilsRepository
import com.example.core.domain.home.add_pet.model.PetModel
import com.example.core.domain.home.add_pet.repository.AddPetRepository
import com.example.core.domain.home.petbuddies.model.toAnimalPostModelDto
import com.example.core.utils.Response
import io.github.jan.supabase.postgrest.Postgrest
import timber.log.Timber
import javax.inject.Inject

class AddPetRepositoryRemote @Inject constructor(
    private val supabaseStorageUtilsRepository: SupabaseStorageUtilsRepository,
    private val postgrest: Postgrest
): AddPetRepository {
    override suspend fun getPet(): Response<PetModel> {
        TODO("Not yet implemented")
    }

    override suspend fun savePet(petModel: PetModel): Response<PetModel> {
        return try {
            TODO("Not yet implemented")
        } catch (e: Exception) {
            Timber.e("Error creating post: ${e.message}")
            Response.Error(e.message ?: "An Unknown Error occurred")
        }
    }

    private fun addPostImageName(username: String, petName: String) = "${username}_${petName}_${System.currentTimeMillis()}.jpg"

    companion object {
        const val PETS = "user_pets"
        const val BUCKET_NAME = "furbook_bucket"
        const val BUCKET_FOLDER = "pets_photo"
    }
}