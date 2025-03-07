package com.example.core.di.home.petbuddies

import com.example.core.data.home.petbuddies.remote.PetBuddiesRepositoryRemote
import com.example.core.domain.home.petbuddies.repository.PetBuddiesRepository
import com.example.core.utils.util.ImageHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {

    @Provides
    @Singleton
    fun providePetBuddiesRepository(
        postgrest: Postgrest,
        storage: Storage,
        imageHelper: ImageHelper,
    ): PetBuddiesRepository {
        return PetBuddiesRepositoryRemote(postgrest, storage, imageHelper)
    }
}
