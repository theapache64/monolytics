package com.github.theapache64.monolytics.screen.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.theapache64.monolytics.data.TimeRepo
import com.github.theapache64.monolytics.data.model.Player
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    // private val timeRepo: TimeRepo
) : ViewModel() {

    var currentPlayer by mutableStateOf<Player?>(null)
        private set

    private var players = mutableListOf<Player>()

    fun init(names: List<String>) {
        val players = names.map { name ->
            Player(
                name = name.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() },
                currentTime = mutableLongStateOf(0L),
                totalTime = mutableListOf()
            )
        }
        this.players.addAll(players)
        currentPlayer = players.first()
        startTimer()
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (true) {
                currentPlayer?.currentTime?.value = currentPlayer?.currentTime?.value?.plus(1000) ?: 0L
                delay(1000L)
            }
        }
    }

    fun onScreenClicked() {
        // change player first
        val currentPlayerIndex = players.indexOfFirst { it.name == currentPlayer?.name }
        val nextPlayerIndex = (currentPlayerIndex + 1) % players.size
        currentPlayer = players[nextPlayerIndex]

        // save previous players time
        val previousPlayer = players[currentPlayerIndex]
        previousPlayer.totalTime.add(previousPlayer.currentTime.value)

        // reset previous player time
        previousPlayer.currentTime.value = 0L
    }

    fun onEndGameClicked() {

    }
}