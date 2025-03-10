package com.example.core.domain.home.petbuddies.usecases

import com.example.core.data.home.petbuddies.remote.PetBuddiesRepositoryRemote
import com.example.core.domain.home.petbuddies.model.AnimalPostModel
import com.example.core.utils.Response
import javax.inject.Inject

class GetAnimalPostsUseCase @Inject constructor(
    private val petBuddiesRepositoryRemote: PetBuddiesRepositoryRemote
) {
    suspend operator fun invoke(): Response<List<AnimalPostModel>> {
        return petBuddiesRepositoryRemote.getAnimalPosts()
    }
}