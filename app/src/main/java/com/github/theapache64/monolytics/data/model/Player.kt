package com.github.theapache64.monolytics.data.model

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State

data class Player(
    val name : String,
    var currentTime : MutableState<Long>,
    val totalTime : MutableList<Long>
)
