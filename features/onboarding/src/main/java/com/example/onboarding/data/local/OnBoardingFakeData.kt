package com.example.onboarding.data.local

import com.example.onboarding.data.model.OnBoardingModel
import com.example.ui.R as UiR

object OnBoardingFakeData {
    val onBoardingData = listOf(
        OnBoardingModel(
            image = UiR.drawable.on_boarding_image_1,
            title =UiR.string.on_boarding_title_1,
            description = UiR.string.on_boarding_description_1
        ),
        OnBoardingModel(
            image = UiR.drawable.on_boarding_image_1,
            title = UiR.string.on_boarding_title_2,
            description = UiR.string.on_boarding_description_2
        ),
        OnBoardingModel(
            image = UiR.drawable.on_boarding_image_1,
            title = UiR.string.on_boarding_title_3,
            description = UiR.string.on_boarding_description_3
        )
    )
}