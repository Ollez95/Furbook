package com.example.core.data.onboarding.local

import com.example.core.data.onboarding.model.OnBoardingModel
import com.example.ui.R as UiR

object OnBoardingFakeData {
    val onBoardingData = listOf(
        OnBoardingModel(
            image = UiR.raw.onboarding_1,
            title =UiR.string.on_boarding_title_1,
            description = UiR.string.on_boarding_description_1
        ),
        OnBoardingModel(
            image = UiR.raw.onboarding_2,
            title = UiR.string.on_boarding_title_2,
            description = UiR.string.on_boarding_description_2
        ),
        OnBoardingModel(
            image = UiR.raw.onboarding_3,
            title = UiR.string.on_boarding_title_3,
            description = UiR.string.on_boarding_description_3
        )
    )
}