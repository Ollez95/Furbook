package com.example.core.domain.onboarding.repository

import com.airbnb.lottie.LottieComposition


interface LottieRepository {
    suspend fun loadAllLottieFiles(): Boolean
    suspend fun preloadLottieFiles(animationResIds: List<Int>): Boolean
    fun getLottieComposition(animationResId: Int): LottieComposition
}
