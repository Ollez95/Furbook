package com.example.onboarding.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.airbnb.lottie.LottieComposition
import com.example.core.domain.onboarding.repository.LottieRepository
import com.example.core.domain.onboarding.repository.OnBoardingRepository
import com.example.datastore.onboarding.WasOnBoardingExecutedDatastore
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
    private val wasOnBoardingExecutedDatastore: WasOnBoardingExecutedDatastore,
    private val lottieRepository: LottieRepository
) : ViewModel() {

    private val _state = MutableStateFlow(OnBoardingState())
    val state: StateFlow<OnBoardingState> = _state

    private val _eventFlow = MutableSharedFlow<OnBoardingEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _composition = MutableStateFlow<LottieComposition?>(null)
    val composition: StateFlow<LottieComposition?> = _composition

    fun getOnBoardingData() {
        val onBoardingData = onBoardingRepository.getOnBoardingData()
        _state.value = OnBoardingState(listBoardingModel = onBoardingData)
    }

    fun onEvent(event: OnBoardingEvent) {
        when (event) {
            is OnBoardingEvent.Finish -> {
                viewModelScope.launch {
                    wasOnBoardingExecutedDatastore.saveValueDatastore(true)
                    _eventFlow.emit(OnBoardingEvent.Finish) // Emit event
                }
            }
        }
    }

    fun getLottieComposition(animationResId: Int): LottieComposition? {
        return lottieRepository.getLottieComposition(animationResId)

    }
}
