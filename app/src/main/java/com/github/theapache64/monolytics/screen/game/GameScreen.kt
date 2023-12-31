package com.github.theapache64.monolytics.screen.game

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

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

    Box(modifier = Modifier.fillMaxSize().clickable { viewModel.onScreenClicked() }) {
        Text(text = viewModel.currentPlayer?.name ?: "No player", modifier = Modifier.align(
            Alignment.Center), fontSize = 50.sp)
    }
}