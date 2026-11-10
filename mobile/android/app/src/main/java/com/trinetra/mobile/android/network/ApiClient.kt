package com.trinetra.mobile.android.network

import com.trinetra.mobile.android.BuildConfig
import com.trinetra.mobile.android.BuildConfig.DefaultConfig
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory

// Network module for TRINETRA API integration
// Uses exact endpoint paths and request schemas from contracts/openapi.yaml
// No backend secrets placed in the APK

object TRINETRAApiClient {

    // Base URL from openapi.yaml server definition
    private const val BASE_URL = "http://localhost:8080/api/"

    // Logging interceptor for debugging (disabled in release)
    private fun loggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC else HttpLoggingInterceptor.Level.NONE
        return logging
    }

    // Authorization interceptor that adds Bearer token
    fun authorizationInterceptor(token: String?): okhttp3.Interceptor {
        return okhttp3.Interceptor { chain ->
            val originalRequest = chain.request()
            val builder = originalRequest.newBuilder()
                .method(originalRequest.method, originalRequest.body)
            // Add Accept-Language header
            builder.header("Accept-Language", "en,hi,kn")
            // Add X-Trace-Id header for request tracing
            builder.header("X-Trace-Id", java.util.UUID.randomUUID().toString())
            // Add Idempotency-Key for safe methods where required
            if (originalRequest.method() == "GET" || originalRequest.method() == "HEAD" || originalRequest.method() == "OPTIONS") {
                builder.header("Idempotency-Key", java.util.UUID.randomUUID().toString())
            }
            // Add Authorization header if token available
            if (token != null) {
                builder.header("Authorization", "Bearer $token")
            }
            builder.header("Accept", "application/json")
            chain.proceed(builder.build())
        }
    }

    // Create Retrofit instance
    fun createApi(
        token: String?
    ): TRINETRAApiService {
        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor())
            .addInterceptor(authorizationInterceptor(token))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create())
            .build()
            .create(TRINETRAApiService::class.java)
    }
}