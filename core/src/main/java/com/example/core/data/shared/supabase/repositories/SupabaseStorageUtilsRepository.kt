package com.example.core.data.shared.supabase.repositories

import android.net.Uri
import com.example.core.data.home.petbuddies.remote.PetBuddiesRepositoryRemote.Companion.BUCKET_FOLDER
import com.example.core.data.home.petbuddies.remote.PetBuddiesRepositoryRemote.Companion.BUCKET_NAME
import com.example.core.utils.util.ImageHelper
import io.github.jan.supabase.storage.Storage
import timber.log.Timber
import javax.inject.Inject

class SupabaseStorageUtilsRepository @Inject constructor(
    private val storage: Storage,
    private val imageHelper: ImageHelper
) {
    suspend fun uploadImageToSupabase(bucketName: String, uri: Uri, routeToSaveFile: String): String? {
        return try {
            val storage = storage.from(bucketName) // Replace with your bucket name
            // Upload image
            val byteArray = imageHelper.uriToByteArray(uri) ?: return null
            storage.upload(routeToSaveFile, byteArray)

            // Generate a public URL
            storage.publicUrl(routeToSaveFile)
        } catch (e: Exception) {
            Timber.e(e.message)
            null // Handle errors (e.g., no internet, permission issues)
        }
    }
}