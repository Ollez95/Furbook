package com.example.core.data.home.petbuddies.remote

import android.net.Uri
import com.example.core.data.home.petbuddies.remote.model.AnimalPostModelDto
import com.example.core.data.home.petbuddies.remote.model.toAnimalPostModel
import com.example.core.domain.home.petbuddies.model.AnimalPostModel
import com.example.core.domain.home.petbuddies.model.toAnimalPostModelDto
import com.example.core.domain.home.petbuddies.repository.PetBuddiesRepository
import com.example.core.utils.Response
import com.example.core.utils.util.ImageHelper
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import timber.log.Timber
import javax.inject.Inject

class PetBuddiesRepositoryRemote @Inject constructor(
    private val postgrest: Postgrest,
    private val storage: Storage,
    private val imageHelper: ImageHelper
) : PetBuddiesRepository {
    override suspend fun getAnimalPosts(): Response<List<AnimalPostModel>> {
        return try {
            val response = postgrest.from(ANIMAL_POSTS).select().decodeList<AnimalPostModelDto>()
            val animalList = response.map { it.toAnimalPostModel() }
            return Response.Success(animalList)
        } catch (e: Exception) {
            Response.Error(e.message ?: "An Unknown Error occurred")
        }
    }

    override suspend fun createAnimalPost(uri: Uri, animalPostModel: AnimalPostModel): Response<Boolean> {
        return try {
            val imageUrl = uploadImageToSupabase(animalPostModel.username, uri) ?: return Response.Error("Failed to upload image")
            val animalPostModelFinal = animalPostModel.copy(imageUrl = imageUrl).toAnimalPostModelDto()
            postgrest
                .from(ANIMAL_POSTS)
                .insert(animalPostModelFinal)
            Response.Success(true)
        } catch (e: Exception) {
            Timber.e("Error creating post: ${e.message}")
            Response.Error(e.message ?: "An Unknown Error occurred")
        }
    }

    override suspend fun getAnimalPostFilteredByAnimals(animals: List<String>): Response<List<AnimalPostModel>> {
        TODO("Not yet implemented")

    }

    private suspend fun uploadImageToSupabase(username: String, uri: Uri): String? {
        return try {
            val storage = storage.from(BUCKET_NAME) // Replace with your bucket name
            val imagePath = BUCKET_FOLDER + addPostImageName(username)

            // Upload image
            val byteArray = imageHelper.uriToByteArray(uri) ?: return null
            storage.upload(imagePath, byteArray)

            // Generate a public URL
            storage.publicUrl(imagePath)
        } catch (e: Exception) {
            Timber.e(e.message)
            null // Handle errors (e.g., no internet, permission issues)
        }
    }

    private fun addPostImageName(username: String) = "${username}_${System.currentTimeMillis()}.jpg"

    companion object {
        const val ANIMAL_POSTS = "animal_post"
        const val BUCKET_NAME = "furbook_bucket"
        const val BUCKET_FOLDER = "posts_photo/"
    }
}