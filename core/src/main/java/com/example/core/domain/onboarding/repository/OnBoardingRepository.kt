package com.example.core.domain.onboarding.repository

import com.example.core.data.onboarding.model.OnBoardingModel

interface OnBoardingRepository {
    fun getOnBoardingData(): List<OnBoardingModel>
}