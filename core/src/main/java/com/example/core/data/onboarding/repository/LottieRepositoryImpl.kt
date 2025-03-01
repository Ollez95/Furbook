package com.example.core.data.onboarding.repository

import android.content.Context
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieCompositionFactory
import com.example.ui.R
import com.example.core.domain.onboarding.repository.LottieRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LottieRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
) : LottieRepository {

    private val compositionCache = mutableMapOf<Int, LottieComposition?>()

    override suspend fun loadAllLottieFiles():Boolean {
        return preloadLottieFiles(
            listOf(
                R.raw.onboarding_1,
                R.raw.onboarding_2,
                R.raw.onboarding_3
            )
        )
    }

    override suspend fun preloadLottieFiles(animationResIds: List<Int>): Boolean {
        return withContext(Dispatchers.Main) {
            val results = animationResIds.map { resId ->
                val result = LottieCompositionFactory.fromRawResSync(context, resId).value
                compositionCache[resId] = result
                result != null // ✅ Returns true if animation was successfully loaded
            }
            results.all { it } // ✅ Return true only if all animations were loaded successfully
        }
    }

    override fun getLottieComposition(animationResId: Int): LottieComposition? {
        return compositionCache[animationResId] ?: run {
            val composition = LottieCompositionFactory.fromRawResSync(context, animationResId).value
            compositionCache[animationResId] = composition
            composition
        }
    }
}