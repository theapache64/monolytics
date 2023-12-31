package com.github.theapache64.monolytics.screen.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.capitalize
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.theapache64.monolytics.data.model.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor() : ViewModel() {

    var currentPlayer by mutableStateOf<Player?>(null)
        private set

    var players = mutableStateListOf<Player>()
        private set

    fun init(names: List<String>) {
        val players = names.map { name -> Player(
            name = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
            currentTime = 0L,
            totalTime = mutableListOf()
        ) }
        this.players.addAll(players)
        currentPlayer = players.first()
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (true) {
                currentPlayer?.currentTime = currentPlayer?.currentTime?.plus(1000) ?: 0L
                delay(1000L)
            }
        }
    }

    fun onScreenClicked() {
        val nextPlayerIndex = (players.indexOf(currentPlayer) + 1) % players.size
        currentPlayer = players[nextPlayerIndex]
    }

    fun onEndGameClicked() {

    }
}