package com.github.theapache64.monolytics.screen.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.theapache64.monolytics.composable.CommaInputUi

@Composable
fun OnboardingScreen(
    onOnboardingFinished: (names : List<String>) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CommaInputUi(
            actionTitle = "START GAME 🚀",
            onInputSubmitted = { names ->
                onOnboardingFinished(names)
            }
        )
    }
}