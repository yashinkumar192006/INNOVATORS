package com.trinetra.mobile.android.core.network.service

import com.trinetra.mobile.android.core.network.api.TRINETRAApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object TRINETRANetworkClient {

    private const val BASE_URL = "http://localhost:8080/api/"

    private val client: TRINETRAApiService

    init {
        // Logging interceptor for development
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

        client = retrofit.create(TRINETRAApiService::class.java)
    }

    fun getApiService(): TRINETRAApiService = client
}