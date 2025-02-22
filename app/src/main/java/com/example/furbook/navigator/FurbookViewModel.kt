package com.example.furbook.navigator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.datastore.authentication.IsUserLoggedInDatastore
import com.example.datastore.onboarding.WasOnBoardingExecutedDatastore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FurbookViewModel @Inject constructor(
    private val wasOnBoardingExecutedDatastore: WasOnBoardingExecutedDatastore,
    private val isUserLoggedInDatastore: IsUserLoggedInDatastore
): ViewModel() {
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _state = MutableStateFlow(FurbookState())
    val state: StateFlow<FurbookState> = _state.asStateFlow()

    init {
        checkStatus()
    }

    private fun checkStatus() {
        viewModelScope.launch {
            // Combine both flows
            combine(
                wasOnBoardingExecutedDatastore.getValueDatastore(),
                isUserLoggedInDatastore.getValueDatastore()
            ) { isOnboardingDone, userToken ->
                Pair(isOnboardingDone, userToken)
            }.collect { (isCompleted, userToken) ->
                _state.update {
                    it.copy(
                        isOnboardingCompleted = isCompleted ?: false,
                        isUserAuthenticated = !userToken.isNullOrEmpty() // Or use Boolean
                    )
                }
                _isLoading.value = false
            }
        }
    }
}