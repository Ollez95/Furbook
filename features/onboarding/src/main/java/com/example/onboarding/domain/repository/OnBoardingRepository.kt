package com.example.onboarding.domain.repository

import com.example.onboarding.data.model.OnBoardingModel

interface OnBoardingRepository {
    fun getOnBoardingData(): List<OnBoardingModel>
}