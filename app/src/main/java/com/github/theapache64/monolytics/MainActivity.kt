package com.github.theapache64.monolytics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.github.theapache64.monolytics.screen.game.GameScreen
import com.github.theapache64.monolytics.screen.onboarding.OnboardingScreen
import com.github.theapache64.monolytics.screen.result.ResultScreen
import com.github.theapache64.monolytics.ui.theme.MonolyticsTheme
import dagger.hilt.android.AndroidEntryPoint

sealed interface Screen {
    data object Onboarding : Screen
    data class Game(
        val names : List<String>
    ) : Screen
    data object Result : Screen
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MonolyticsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Main()
                }
            }
        }
    }

    @Composable
    fun Main(){
        var screen by remember { mutableStateOf<Screen>(Screen.Onboarding) }
        when(screen){
            Screen.Onboarding -> OnboardingScreen(
                onOnboardingFinished = { names ->
                    screen = Screen.Game(names)
                }
            )
            is Screen.Game -> GameScreen(
                names = (screen as Screen.Game).names,
                onGameFinished = {
                    screen = Screen.Result
                }
            )
            Screen.Result -> ResultScreen(
                onResultSubmitted = {
                    finish()
                }
            )
        }

        BackHandler {
            // disable back navigation
        }
    }
}