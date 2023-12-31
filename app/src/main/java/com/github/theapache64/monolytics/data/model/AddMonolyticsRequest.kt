package com.github.theapache64.monolytics.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddMonolyticsRequest(
    @Json(name = "name") val name: String,
    @Json(name = "current_time") val currentTime: String,
    @Json(name = "total_time") val totalTime: String,
    @Json(name = "am_i_bad") val amIBad: String
)