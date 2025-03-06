package com.example.core.domain.home.petbuddies.usecases

import com.example.core.data.home.petbuddies.remote.PetBuddiesRepositoryRemote
import com.example.core.domain.home.petbuddies.model.AnimalPostModel
import com.example.core.utils.Response
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CreateAnimalPostUseCase @Inject constructor(
    private val petBuddiesRepositoryRemote: PetBuddiesRepositoryRemote
) {
    fun invoke(animalPostModel: AnimalPostModel): Flow<Response<Boolean>> {
        return petBuddiesRepositoryRemote.createAnimalPost(animalPostModel)
    }
}