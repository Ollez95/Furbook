package com.example.core.navigation

import com.example.core.domain.authentication.login.usecase.IsUserLoggedInUseCase
import com.example.core.navigation.model.NavigationResponse
import com.example.core.utils.Response
import com.example.datastore.onboarding.WasOnBoardingExecutedDatastore
import javax.inject.Inject

class NavigationHelperRepository @Inject constructor(
    private val wasOnBoardingExecutedDatastore: WasOnBoardingExecutedDatastore,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase,
) {
    // ✅ Use suspend function instead of Flow
    suspend fun checkNavigationStateOnce(): NavigationResponse {
        var token = ""

        val wasOnboardingExecuted = try {
            wasOnBoardingExecutedDatastore.getValueDataStoreOnce() ?: false
        } catch (_: Exception) {
            false
        }

        val isLoggedIn = try {
            when (val loginResponse = isUserLoggedInUseCase.invoke()) {
                is Response.Success -> {
                    token = loginResponse.data
                    true
                }
                else -> false
            }
        } catch (_: Exception) {
            false
        }

        return NavigationResponse(wasOnboardingExecuted, isLoggedIn, token)
    }
}
