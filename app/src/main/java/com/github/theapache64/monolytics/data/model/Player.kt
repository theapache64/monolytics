package com.github.theapache64.monolytics.data.model

import androidx.compose.runtime.State

data class Player(
    val name : String,
    var currentTime : Long,
    val totalTime : MutableList<Long>
)
