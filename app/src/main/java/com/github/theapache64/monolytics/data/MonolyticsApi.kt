package com.github.theapache64.monolytics.data

import com.github.theapache64.monolytics.data.model.AddMonolyticsRequest
import com.github.theapache64.retrosheet.annotations.Write
import retrofit2.http.Body
import retrofit2.http.POST

interface MonolyticsApi {
    @Write
    @POST("add_monolytics") // form name
    suspend fun addMonolytics(
        @Body addMonolyticsRequest: AddMonolyticsRequest
    ): AddMonolyticsRequest
}