package com.example.core.domain.home.petbuddies.usecases

import android.net.Uri
import com.example.core.data.home.petbuddies.remote.PetBuddiesRepositoryRemote
import com.example.core.domain.home.petbuddies.model.AnimalPostModel
import com.example.core.utils.Response
import javax.inject.Inject

class CreateAnimalPostUseCase @Inject constructor(
    private val petBuddiesRepositoryRemote: PetBuddiesRepositoryRemote
) {
    suspend operator fun invoke(uri: Uri, animalPostModel: AnimalPostModel): Response<Boolean> {
        return petBuddiesRepositoryRemote.createAnimalPost(uri, animalPostModel)
    }
}