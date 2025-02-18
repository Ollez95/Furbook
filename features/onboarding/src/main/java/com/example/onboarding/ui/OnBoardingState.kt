package com.example.onboarding.ui

import com.example.core.data.onboarding.model.OnBoardingModel

data class OnBoardingState(
    val listBoardingModel: List<OnBoardingModel> = emptyList()
)