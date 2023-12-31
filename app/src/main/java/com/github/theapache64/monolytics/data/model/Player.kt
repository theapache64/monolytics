package com.github.theapache64.monolytics.data.model

data class Player(
    val name : String,
    var currentTime : Long,
    val totalTime : MutableList<Long>
)
