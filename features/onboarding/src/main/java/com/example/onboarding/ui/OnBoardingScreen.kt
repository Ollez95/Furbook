package com.example.onboarding.ui

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.airbnb.lottie.LottieComposition
import com.example.core.data.onboarding.model.OnBoardingModel
import com.example.navigation.AuthenticationNavigation
import com.example.navigation.OnBoardingNavigation
import com.example.navigation.navigateToDestinationCleaningStack
import com.example.ui.composables.AnimatedPreloader
import com.example.ui.theme.FurbookTheme
import com.example.ui.theme.Space.space2XSmall
import com.example.ui.theme.Space.spaceLarge
import com.example.ui.theme.Space.spaceMedium
import com.example.ui.theme.Space.spaceXSmall
import kotlinx.coroutines.launch

@Composable
fun OnBoardingScreen(
    viewModel: OnBoardingViewModel = hiltViewModel(),
    navController: NavController,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.getOnBoardingData()
        viewModel.eventFlow.collect { event ->
            when (event) {
                is OnBoardingEvent.Finish ->
                    navController
                        .navigateToDestinationCleaningStack(
                            navigationToClean = OnBoardingNavigation.Onboarding,
                            navigateToDestination =  AuthenticationNavigation.Login)
            }
        }
    }

    OnBoardingContent(
        state = state,
        onEvent = { event -> viewModel.onEvent(event) },
        addLottieComposition = { viewModel.getLottieComposition(it) })
}


@Composable
fun OnBoardingContent(
    state: OnBoardingState = OnBoardingState(),
    onEvent: (OnBoardingEvent) -> Unit = {},
    addLottieComposition: (Int) -> LottieComposition,
) {
    val onBoardingPages = state.listBoardingModel
    val pagerState = rememberPagerState(pageCount = { onBoardingPages.size })
    val coroutineScope = rememberCoroutineScope()

    Scaffold { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Skip button in the top-right
            TextButton(
                onClick = { onEvent(OnBoardingEvent.Finish) }, modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(spaceMedium)
                    .testTag("skip_button")
            ) {
                Text("Skip")
            }

            Column(
                modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center
            ) {
                HorizontalPager(state = pagerState) { page ->
                    val compositionObject = addLottieComposition(onBoardingPages[page].image)
                    OnboardingPageContent(
                        page = onBoardingPages[page],
                        composition = compositionObject
                    )
                }

                // Page Indicators
                Row(
                    modifier = Modifier.padding(top = spaceMedium), horizontalArrangement = Arrangement.Center
                ) {
                    onBoardingPages.indices.forEach { index ->
                        Indicator(isSelected = pagerState.currentPage == index)
                    }
                }
            }

            // ✅ Position Next/Back buttons 32dp from bottom
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp, start = spaceMedium, end = spaceMedium), // Ensure spacing
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                if (pagerState.currentPage > 0) {
                    TextButton(
                        modifier = Modifier.testTag("back_button"),
                        onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pagerState.currentPage - 1)
                        }
                    }) {
                        Text("Back")
                    }
                }

                Button(
                    modifier = Modifier.testTag("next_button"),
                    onClick = {
                    coroutineScope.launch {
                        if (pagerState.currentPage == onBoardingPages.lastIndex) {
                            onEvent(OnBoardingEvent.Finish)
                        } else {
                            pagerState.animateScrollToPage(pagerState.currentPage + 1)
                        }
                    }
                }) {
                    Text(if (pagerState.currentPage == onBoardingPages.lastIndex) "Get Started" else "Next")
                }
            }
        }
    }
}




@Composable
fun OnboardingPageContent(page: OnBoardingModel, composition: LottieComposition) {
    Column(Modifier.padding(spaceMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center)
    {

        AnimatedPreloader(sizeLottie = 250.dp, composition = composition)

        Spacer(modifier = Modifier.height(spaceLarge))
        Text(text = stringResource(id = page.title), style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(spaceXSmall))
        Text(text = stringResource(id = page.description), style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .size(spaceMedium)
            .padding(space2XSmall)
            .clip(CircleShape)
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
            )
    )
}


@Preview(
    name = "Light Mode", showBackground = true
)
@Preview(
    name = "Dark Mode", showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES // Enables dark mode preview
)
@Composable
private fun OnBoardingScreenPreview() {
    FurbookTheme {
        //val onBoardingState = OnBoardingState(listBoardingModel = OnBoardingFakeData.onBoardingData)
        //OnBoardingContent(onBoardingState)
    }
}