package com.example.furbook.navigator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.navigation.NavigationHelperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class FurbookViewModel @Inject constructor(
    navigationHelperRepository: NavigationHelperRepository,
) : ViewModel() {

    // ✅ Use stateIn to prevent re-executions
    val state: StateFlow<FurbookState> =
        navigationHelperRepository
        .checkNavigationState()
        .map { (isCompleted, isUserLoggedIn) ->
            FurbookState(
                isOnboardingCompleted = isCompleted,
                isUserAuthenticated = isUserLoggedIn,
                isLoading = false
            )
        }.stateIn(
                scope = viewModelScope,
                // 👇 Fix: Use WhileSubscribed to limit re-executions
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = FurbookState(isLoading = true)
            )

}