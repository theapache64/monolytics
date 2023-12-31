package com.github.theapache64.monolytics.data

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TimeRepo @Inject constructor(
    private val monolyticsApi: MonolyticsApi,
) : MonolyticsApi by monolyticsApi