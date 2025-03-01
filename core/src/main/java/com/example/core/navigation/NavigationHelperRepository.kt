package com.example.core.navigation

import com.example.core.domain.authentication.login.usecase.IsUserLoggedInUseCase
import com.example.core.domain.onboarding.repository.LottieRepository
import com.example.core.navigation.model.NavigationResponse
import com.example.core.utils.Response
import com.example.datastore.onboarding.WasOnBoardingExecutedDatastore
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.first
import timber.log.Timber
import javax.inject.Inject

class NavigationHelperRepository @Inject constructor(
    private val wasOnBoardingExecutedDatastore: WasOnBoardingExecutedDatastore,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
    private val auth: Auth,
    private val lottieRepository: LottieRepository) {

    suspend fun checkNavigationStateOnce(): NavigationResponse {
        var isLoggedIn = false

        val wasOnboardingExecuted = try {
            val onBoardingDataStore = wasOnBoardingExecutedDatastore.getValueDataStoreOnce() ?: false
            val lottieFilesWereLoaded = lottieRepository.loadAllLottieFiles()
            onBoardingDataStore && lottieFilesWereLoaded
        } catch (_: Exception) {
            false
        }

        if(wasOnboardingExecuted){
            awaitSessionInitialization()
            isLoggedIn = try {
                when (isUserLoggedInUseCase.invoke()) {
                    is Response.Success -> {
                        true
                    }
                    else -> false
                }
            } catch (_: Exception) {
                false
            }
        }

        return NavigationResponse(wasOnboardingExecuted, isLoggedIn)
    }

    private suspend fun awaitSessionInitialization() {
        auth.sessionStatus.first { status ->
            Timber.d(status.toString())
            status !is SessionStatus.Initializing
        }
    }
}
