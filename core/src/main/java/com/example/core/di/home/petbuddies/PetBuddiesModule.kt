package com.example.core.di.home.petbuddies

import com.example.core.data.authentication.repository.remote.AuthenticationRepositoryImpl
import com.example.core.data.home.petbuddies.remote.PetBuddiesRepositoryRemote
import com.example.core.domain.authentication.repository.AuthenticationRepository
import com.example.core.domain.home.petbuddies.repository.PetBuddiesRepository
import com.example.core.utils.util.ImageHelper
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.auth.Auth
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
        auth: Auth
    ): PetBuddiesRepository {
        return PetBuddiesRepositoryRemote(postgrest, storage, imageHelper, auth)
    }
}
