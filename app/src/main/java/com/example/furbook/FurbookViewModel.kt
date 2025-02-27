package com.example.furbook

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.navigation.NavigationHelperRepository
import com.example.furbook.navigator.FurbookState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FurbookViewModel @Inject constructor(
    private val navigationHelperRepository: NavigationHelperRepository
) : ViewModel() {

    private val _state = MutableStateFlow(FurbookState(isLoading = true))
    val state: StateFlow<FurbookState> = _state
        .onStart { loadData() } // ✅ Ensure state updates with email
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = FurbookState(isLoading = true)
        )

    private fun loadData() {
        viewModelScope.launch {
            val navigationResponse = navigationHelperRepository.checkNavigationStateOnce()
            _state.value = FurbookState(
                isOnboardingCompleted = navigationResponse.isOnboardingCompleted,
                isUserAuthenticated = navigationResponse.isUserAuthenticated,
                isLoading = false
            )
        }
    }
}