package com.example.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.onboarding.domain.repository.OnBoardingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val onBoardingRepository: OnBoardingRepository,
) : ViewModel() {

    private val _state = MutableStateFlow(OnBoardingState())
    val state: StateFlow<OnBoardingState> = _state

    private val _eventFlow = MutableSharedFlow<OnBoardingEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun getOnBoardingData() {
        val onBoardingData = onBoardingRepository.getOnBoardingData()
        _state.value = OnBoardingState(listBoardingModel = onBoardingData)
    }

    fun onEvent(event: OnBoardingEvent) {
        when (event) {
            is OnBoardingEvent.Finish -> {
                viewModelScope.launch {
                    _eventFlow.emit(OnBoardingEvent.Finish) // Emit event
                }
            }
        }
    }
}
