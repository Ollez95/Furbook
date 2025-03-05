package com.example.core.data.home.petbuddies.remote

import android.net.Uri
import com.example.core.domain.home.petbuddies.model.AnimalPostModel
import com.example.core.domain.home.petbuddies.repository.PetBuddiesRepository
import com.example.core.utils.Response
import com.example.core.utils.util.ImageHelper
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PetBuddiesRepositoryRemote @Inject constructor(
    private val postgrest: Postgrest,
    private val storage: Storage,
    private val imageHelper: ImageHelper,
    private val auth: Auth
) : PetBuddiesRepository {
    override suspend fun getAnimalPosts(): Response<List<AnimalPostModel>> {
        return try {
            val response = postgrest.from(ANIMAL_POSTS).select().decodeList<AnimalPostModel>()
            return Response.Success(response)
        } catch (e: Exception) {
            Response.Error(e.message ?: "An Unknown Error occurred")
        }
    }

    override fun createAnimalPost(animalPostModel: AnimalPostModel): Flow<Response<Boolean>> = flow {
        emit(Response.Loading)
        try {
            val imageUrl = uploadImageToSupabase(animalPostModel.imageUrl)
                ?: emit(Response.Error("Failed to upload image"))

            val tagsJson = animalPostModel.tags.map {
                mapOf("tag" to it.tag, "color" to it.color.toString())
            }
            postgrest
                .from(ANIMAL_POSTS)
                .insert(
                    mapOf(
                        "id" to animalPostModel.id,
                        "name" to animalPostModel.name,
                        "animal" to animalPostModel.animal,
                        "image_url" to imageUrl,
                        "description" to animalPostModel.description,
                        "tags" to tagsJson,
                        "userId" to auth.currentSessionOrNull()?.user?.id
                    )
                )

            emit(Response.Success(true))
        } catch (e: Exception) {
            emit(Response.Error(e.message ?: "An Unknown Error occurred"))
        }
    }

    override suspend fun getAnimalPostFilteredByAnimals(animals: List<String>): Response<List<AnimalPostModel>> {
        TODO("Not yet implemented")

    }

    private suspend fun uploadImageToSupabase(uri: Uri): String? {
        return try {
            val storage = storage.from(BUCKET_NAME) // Replace with your bucket name
            val imagePath = BUCKET_FOLDER + uri.lastPathSegment

            // Upload image
            val byteArray = imageHelper.uriToByteArray(uri) ?: return null
            storage.upload(imagePath, byteArray)

            // Generate a public URL
            storage.publicUrl(imagePath)
        } catch (e: Exception) {
            null // Handle errors (e.g., no internet, permission issues)
        }
    }

    companion object {
        const val ANIMAL_POSTS = "animal_posts"
        const val BUCKET_NAME = "furbook_bucket"
        const val BUCKET_FOLDER = "posts_photo/"
    }
}