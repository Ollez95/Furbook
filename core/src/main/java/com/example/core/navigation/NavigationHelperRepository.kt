package com.example.core.navigation

import com.example.core.domain.authentication.login.usecase.IsUserLoggedInUseCase
import com.example.core.utils.Response
import com.example.datastore.onboarding.WasOnBoardingExecutedDatastore
import javax.inject.Inject

class NavigationHelperRepository @Inject constructor(
    private val wasOnBoardingExecutedDatastore: WasOnBoardingExecutedDatastore,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
) {
    // ✅ Use suspend function instead of Flow
    suspend fun checkNavigationStateOnce(): Pair<Boolean, Boolean> {

        val wasOnboardingExecuted = try {
            wasOnBoardingExecutedDatastore.getValueDataStoreOnce() ?: false
        } catch (_: Exception) {
            false
        }

        val isLoggedIn = try {
            isUserLoggedInUseCase.invoke() is Response.Success
        } catch (_: Exception) {
            false
        }

        return Pair(wasOnboardingExecuted, isLoggedIn)
    }
}
