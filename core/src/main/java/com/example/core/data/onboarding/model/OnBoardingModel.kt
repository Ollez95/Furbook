package com.example.core.data.onboarding.model

import androidx.annotation.RawRes
import androidx.annotation.StringRes

data class OnBoardingModel(
    @RawRes val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int
)