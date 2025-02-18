package com.example.core.data.onboarding.repository

import com.example.core.data.onboarding.local.OnBoardingFakeData
import com.example.core.data.onboarding.model.OnBoardingModel
import com.example.core.domain.onboarding.repository.OnBoardingRepository

class OnBoardingRepositoryImpl: OnBoardingRepository {
    override fun getOnBoardingData(): List<OnBoardingModel> {
        return OnBoardingFakeData.onBoardingData
    }
}