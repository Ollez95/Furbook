package com.example.core.data.onboarding.repository

import android.content.Context
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.LottieCompositionFactory
import com.example.core.di.module.IoDispatcher
import com.example.ui.R
import com.example.core.domain.onboarding.repository.LottieRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LottieRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher // ✅ Inject dispatcher
) : LottieRepository {

    private val compositionCache = mutableMapOf<Int, LottieComposition>()

    override suspend fun loadAllLottieFiles(): Boolean {
        return preloadLottieFiles(
            listOf(
                R.raw.onboarding_1,
                R.raw.onboarding_2,
                R.raw.onboarding_3
            )
        )
    }

    override suspend fun preloadLottieFiles(animationResIds: List<Int>): Boolean {
        return withContext(ioDispatcher) {
            val results = animationResIds.map { resId ->
                val result = LottieCompositionFactory.fromRawResSync(context, resId).value
                if (result != null) {
                    compositionCache[resId] = result // ✅ Store in cache only if valid
                }
                result != null
            }
            results.all { it } // ✅ Return true only if all animations were successfully loaded
        }
    }

    override fun getLottieComposition(animationResId: Int): LottieComposition {
        return compositionCache[animationResId] ?: run {
            val defaultResId = R.raw.onboarding_1 // ✅ Use a default animation
            val fallbackComposition = LottieCompositionFactory.fromRawResSync(context, defaultResId).value
                ?: throw IllegalStateException("Default Lottie animation failed to load") // ✅ Ensure it's never null
            compositionCache[animationResId] = fallbackComposition
            fallbackComposition
        }
    }
}
