package com.example.onboarding.ui

sealed interface OnBoardingEvent {
    data object Finish : OnBoardingEvent
}
