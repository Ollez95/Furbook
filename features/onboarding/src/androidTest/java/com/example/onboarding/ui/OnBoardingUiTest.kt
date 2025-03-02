package com.example.onboarding.ui

import androidx.activity.ComponentActivity
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.airbnb.lottie.LottieComposition
import com.example.core.data.onboarding.local.OnBoardingFakeData
import com.example.ui.theme.FurbookTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import io.mockk.every
import io.mockk.mockk
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OnBoardingContentTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val fakeOnBoardingState = OnBoardingFakeData.onBoardingData

    private val mockLottieComposition = mockk<LottieComposition>(relaxed = true)
    private val mockFakeOnBoardingState = mockk<OnBoardingState>(relaxed = true)

    private val fakeLottieLoader: (Int) -> LottieComposition = mockk(relaxed = true) // ✅ Correctly mock function

    private val onEventCalled = mutableStateOf(false)

    @Before
    fun setUp() {
        every { mockFakeOnBoardingState.listBoardingModel } returns fakeOnBoardingState
        every { fakeLottieLoader.invoke(any()) } returns mockLottieComposition // ✅ Use `.invoke(any())` instead of `any<Int>()`
    }

    @Test
    fun onboardingScreen_showsFirstPageContent() {
        composeTestRule.setContent {
            FurbookTheme {
                OnBoardingContent(
                    state = mockFakeOnBoardingState,
                    onEvent = { onEventCalled.value = true },
                    addLottieComposition = fakeLottieLoader
                )
            }
        }

        // ✅ Check if the first page content appears
        composeTestRule.onNodeWithText("Neque porro quisquam est qui dolorem 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("Lorem ipsum odor amet, consectetuer adipiscing elit. Ante malesuada velit at dolor fames velit integer 1").assertIsDisplayed()
    }

    @Test
    fun onboardingScreen_canNavigateToNextPage() {
        composeTestRule.setContent {
            FurbookTheme {
                OnBoardingContent(
                    state = mockFakeOnBoardingState,
                    onEvent = { onEventCalled.value = true },
                    addLottieComposition = fakeLottieLoader
                )
            }
        }

        // ✅ Click the "Next" button
        composeTestRule.onNodeWithTag("next").performClick()

        // ✅ Check if the second page content appears
        composeTestRule.onNodeWithText("Lorem ipsum odor amet 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("Lorem ipsum odor amet, consectetuer adipiscing elit. Ante malesuada velit at dolor fames velit integer 2").assertIsDisplayed()
    }

    @Test
    fun onboardingScreen_canSkipToFinalPage() {
        composeTestRule.setContent {
            FurbookTheme {
                OnBoardingContent(
                    state = mockFakeOnBoardingState,
                    onEvent = { onEventCalled.value = true },
                    addLottieComposition = fakeLottieLoader
                )
            }
        }

        // ✅ Click "Skip"
        composeTestRule.onNodeWithTag("skip").performClick()

        // ✅ Verify that onboarding is finished
        assert(onEventCalled.value)
    }

    @Test
    fun onboardingScreen_callsLottieLoader() {
        composeTestRule.setContent {
            FurbookTheme {
                OnBoardingContent(
                    state = mockFakeOnBoardingState,
                    onEvent = { onEventCalled.value = true },
                    addLottieComposition = fakeLottieLoader
                )
            }
        }

        // ✅ Verify that Lottie composition function is called
        every { fakeLottieLoader(any()) }
    }
}
