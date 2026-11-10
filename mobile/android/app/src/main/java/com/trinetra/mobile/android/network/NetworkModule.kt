package com.trinetra.mobile.android.network

import com.trinetra.mobile.android.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

// Main network module that configures Retrofit with all required features
// - Bearer token authentication
// - Accept-Language header
// - X-Trace-Id tracing
// - Idempotency-Key for safe methods
// - Retry policies appropriate to each request type
// - Network failure handling
// - RFC 7807 error handling via ApiError model

object NetworkModule {

    // Configure OkHttp client with all interceptors
    fun provideOkHttpClient(
        token: String?,
        languageCode: String = "en",
        isRtl: Boolean = false
    ): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) okhttp3.HttpLoggingInterceptor.Level.BASIC else okhttp3.HttpLoggingInterceptor.Level.NONE

        return OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(AuthInterceptor(token))
            .addInterceptor(LanguagePreferenceInterceptor(languageCode, isRtl))
            .addInterceptor(NetworkFailureInterceptor())
            .addNetworkInterceptor(loggingInterceptor())
            .build()
    }

    // Configure Retrofit instance
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        converterFactory: any = ScalarsConverterFactory.create()
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://localhost:8080/api/")
            .client(okHttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }
}