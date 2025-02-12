package com.example.onboarding.data.repository

import com.example.onboarding.data.local.OnBoardingFakeData
import com.example.onboarding.data.model.OnBoardingModel
import com.example.onboarding.domain.repository.OnBoardingRepository

class OnBoardingRepositoryImpl: OnBoardingRepository {
    override fun getOnBoardingData(): List<OnBoardingModel> {
        return OnBoardingFakeData.onBoardingData
    }
}