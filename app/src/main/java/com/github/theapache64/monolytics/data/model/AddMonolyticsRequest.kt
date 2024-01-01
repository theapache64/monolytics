package com.github.theapache64.monolytics.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AddMonolyticsRequest(
    @Json(name = "name") val name: String,
    @Json(name = "time_took_ms") val timeTookMs: String,
    @Json(name = "total_time_took_ms") val totalTimeTookMs: String,
    @Json(name = "time_took") val timeTook: String,
    @Json(name = "total_time_took") val totalTimeTook: String,
    @Json(name = "am_i_bad") val amIBad: String
)