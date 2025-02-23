package com.example.core.navigation

import com.example.core.domain.authentication.login.usecase.IsUserLoggedInUseCase
import com.example.core.utils.Response
import com.example.datastore.onboarding.WasOnBoardingExecutedDatastore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NavigationHelperRepository @Inject constructor(
    private val wasOnBoardingExecutedDatastore: WasOnBoardingExecutedDatastore,
    private val isUserLoggedInUseCase: IsUserLoggedInUseCase
    )
{
    fun checkNavigationState(): Flow<Pair<Boolean, Boolean>> =
        combine(
            wasOnBoardingExecutedDatastore.getValueDatastore()
                .map { it ?: false }
                .distinctUntilChanged(),
            isUserLoggedInUseCase.invoke()
                .map
                { response ->
                response is Response.Success // Convert to Boolean
            }.distinctUntilChanged()
        ) { wasOnboardingExecuted, isLoggedIn ->
            Pair(wasOnboardingExecuted, isLoggedIn)
        }
}