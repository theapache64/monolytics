package com.github.theapache64.monolytics.di

import com.github.theapache64.monolytics.data.MonolyticsApi
import com.github.theapache64.retrosheet.RetrosheetInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

// Network module with Retrofit instance
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideMonolyticsApi(okHttpClient: OkHttpClient): MonolyticsApi {
        return Retrofit.Builder()
            .baseUrl("https://docs.google.com/spreadsheets/d/1VkoZZ1CfOfIp7ix54SE8mIvHHHAxQcAxKZ7x02OH0Wo/")
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create(MonolyticsApi::class.java)
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val retrosheetInterceptor = RetrosheetInterceptor.Builder()
            .setLogging(true)
            .addSheet("monolytics", "created_at", "name", "time_took_ms", "total_time_took_ms", "time_took", "total_time_took", "am_i_bad")
            .addForm("add_monolytics", "https://docs.google.com/forms/d/e/1FAIpQLSebErCHL-jWsx5DWbpRw1Ff_HL0wpqtvzTQrC9KyqbEG9nAhg/viewform?usp=sf_link")
            .build()

        return OkHttpClient.Builder()
            .addInterceptor(retrosheetInterceptor) // and attach the interceptor
            .build()
    }


}