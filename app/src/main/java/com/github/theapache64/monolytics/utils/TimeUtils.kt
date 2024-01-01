package com.github.theapache64.monolytics.utils

object TimeUtils {

    // To convert millisecond to 00:00 format
    fun Long.formatToMinuteSecond(): String {
        return String.format(
            "%02d:%02d",
            this / 1000 / 60,
            this / 1000 % 60
        )
    }
}