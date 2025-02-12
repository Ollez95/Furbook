package com.example.onboarding.data.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class OnBoardingModel(
    @DrawableRes val image: Int,
    @StringRes val title: Int,
    @StringRes val description: Int
)