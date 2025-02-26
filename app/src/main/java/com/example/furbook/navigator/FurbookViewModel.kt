package com.example.furbook.navigator

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.navigation.NavigationHelperRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FurbookViewModel @Inject constructor(
    private val navigationHelperRepository: NavigationHelperRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(FurbookState(isLoading = true))
    val state: StateFlow<FurbookState> = _state.asStateFlow()

    init {
        checkStatus()
    }

    private fun checkStatus() {
        viewModelScope.launch {
            val navigationResponse = navigationHelperRepository.checkNavigationStateOnce()
            _state.value = FurbookState(
                isOnboardingCompleted = navigationResponse.isOnboardingCompleted,
                isUserAuthenticated = navigationResponse.isUserAuthenticated,
                isLoading = false,
                userId = navigationResponse.userId
            )
        }
    }
}