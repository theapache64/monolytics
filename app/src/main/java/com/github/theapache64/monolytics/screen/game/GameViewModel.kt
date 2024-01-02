package com.github.theapache64.monolytics.screen.game

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.theapache64.monolytics.data.TimeRepo
import com.github.theapache64.monolytics.data.model.AddMonolyticsRequest
import com.github.theapache64.monolytics.data.model.Player
import com.github.theapache64.monolytics.utils.TimeUtils.formatToMinuteSecond
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val timeRepo: TimeRepo,
) : ViewModel() {

    var currentPlayer by mutableStateOf<Player?>(null)
        private set

    var stats by mutableStateOf<String?>(null)
        private set

    var slowestPlayer by mutableStateOf<String?>(null)
        private set

    var fastestPlayer by mutableStateOf<String?>(null)
        private set

    var attention by mutableStateOf<String?>(null)
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
                currentPlayer?.currentTime?.value =
                    currentPlayer?.currentTime?.value?.plus(1000) ?: 0L
                delay(1000L)
                val playerStats =
                    players.joinToString("\n") { "👷‍♂️ ${it.name} : ${(it.currentTime.value + it.totalTime.sum()).formatToMinuteSecond()}" }
                stats = playerStats

                // minute alert
                val isCurrentPlayerTakingMoreThanAMinuteToPlay = (currentPlayer?.currentTime?.value ?: 0L) > 60000L
                val minutes = (currentPlayer?.currentTime?.value ?: 0L) / 1000 / 60
                attention = if(isCurrentPlayerTakingMoreThanAMinuteToPlay){
                    "Attention ${currentPlayer?.name}, You're taking too much time to play! ($minutes minutes)"
                }else{
                    null
                }
            }
        }

        viewModelScope.launch {
            while(true){
                delay(60_000L)
                // slowest and fastest
                val isEveryOnePlayedAtLeastOneRound = players.all { it.totalTime.isNotEmpty() }
                if(isEveryOnePlayedAtLeastOneRound){
                    val slowest = players.maxByOrNull { it.currentTime.value + it.totalTime.sum() }
                    val fastest = players.minByOrNull { it.currentTime.value + it.totalTime.sum() }

                    slowestPlayer = "Slowest Player is ${slowest?.name}"
                    fastestPlayer = "Fastest Player is ${fastest?.name}"
                }

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
        var timeTook = previousPlayer.currentTime.value
        previousPlayer.totalTime.add(timeTook)

        // send data to server
        val totalTimeTook = previousPlayer.totalTime.sum()
        val addMonolyticsRequest = AddMonolyticsRequest(
            name = previousPlayer.name,
            timeTook = timeTook.formatToMinuteSecond(),
            timeTookMs = timeTook.toString(),
            totalTimeTook = totalTimeTook.formatToMinuteSecond(),
            totalTimeTookMs = totalTimeTook.toString(),
            amIBad = "-" // TODO: change to boolean
        )

        // sync data
        viewModelScope.launch {
            timeRepo.addMonolytics(
                addMonolyticsRequest = addMonolyticsRequest
            )
        }


        // reset previous player time
        previousPlayer.currentTime.value = 0L
    }

    fun onEndGameClicked() {

    }
}

