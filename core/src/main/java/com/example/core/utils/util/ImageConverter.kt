package com.example.core.utils.util

import android.content.Context
import android.net.Uri
import javax.inject.Inject

class ImageHelper @Inject constructor(private val context: Context) {
    fun uriToByteArray(uri: Uri): ByteArray? {
        return try {
            context.contentResolver.openInputStream(uri)?.use { it.readBytes() }
        } catch (e: Exception) {
            null
        }
    }
}