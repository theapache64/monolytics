package com.github.theapache64.monolytics.screen.game

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.speech.tts.TextToSpeech
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.theapache64.monolytics.data.model.Player
import com.github.theapache64.monolytics.utils.TimeUtils.formatToMinuteSecond

@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun GameScreen(
    viewModel: GameViewModel = hiltViewModel(),
    names: List<String>,
    modifier: Modifier = Modifier,
    onGameFinished: () -> Unit,
) {
    // Change the orientation to landscape and back to portrait
    val activity = LocalContext.current as Activity
    DisposableEffect(Unit) {
        // change activity orientation
        viewModel.init(names)
        activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        onDispose {
            // reset activity orientation to portrait
            activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
    }

    Box(modifier = modifier
        .fillMaxSize()
        .clickable { viewModel.onScreenClicked() }
    ) {

        viewModel.currentPlayer?.let { currentPlayer ->
            CurrentPlayerUi(currentPlayer, modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun CurrentPlayerUi(
    currentPlayer: Player,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val textToSpeech = remember { TextToSpeech(context){} }
    LaunchedEffect(currentPlayer.name) {
        textToSpeech.speak(currentPlayer.name, TextToSpeech.QUEUE_FLUSH, null, null)
    }

    // free TTS resources
    DisposableEffect(textToSpeech){
        onDispose {
            textToSpeech.stop()
            textToSpeech.shutdown()
        }
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Name
        Text(text = currentPlayer.name, fontSize = 60.sp)

        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Current time
            Text(text = currentPlayer.currentTime.value.formatToMinuteSecond(), fontSize = 30.sp)

            // Total time
            Text(
                text = "(${(currentPlayer.currentTime.value + currentPlayer.totalTime.sum()).formatToMinuteSecond()})",
                fontSize = 25.sp
            )
        }

        Button(
            onClick = {

            },
            modifier = Modifier.padding(top = 20.dp)
        ) {
            Text(text = "END GAME")
        }
    }
}
