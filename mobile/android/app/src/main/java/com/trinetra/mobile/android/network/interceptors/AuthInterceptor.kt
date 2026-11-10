package com.trinetra.mobile.android.network.interceptors

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.util.UUID

// Authorization interceptor - adds Bearer token to all requests
class AuthInterceptor(token: String?) : Interceptor {
    override fun intercept(chain: okhttp3.Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()
            .method(originalRequest.method, originalRequest.body)
        // Add Accept-Language header for i18n support
        builder.header("Accept-Language", "en,hi,kn")
        // Add X-Trace-Id for request tracing across services
        builder.header("X-Trace-Id", UUID.randomUUID().toString())
        // Add Idempotency-Key for safe methods to prevent duplicate operations
        if (originalRequest.method() == "GET" || originalRequest.method() == "HEAD" || originalRequest.method() == "OPTIONS") {
            builder.header("Idempotency-Key", UUID.randomUUID().toString())
        }
        // Add Authorization header if token available
        if (token != null) {
            builder.header("Authorization", "Bearer $token")
        }
        builder.header("Content-Type", "application/json")
        chain.proceed(builder.build())
    }
}

// Network failure interceptor - handles network errors gracefully
class NetworkFailureInterceptor : Interceptor {
    override fun intercept(chain: okhttp3.Interceptor.Chain): Response {
        try {
            return chain.proceed(chain.request())
        } catch (e: Exception) {
            // Return a generic network error response
            return Response.Builder()
                .code(0)
                .message("Network error: ${e.message}")
                .build()
        }
    }
}

// Language preference interceptor - persists user's language choice
class LanguagePreferenceInterceptor(
    private val languageCode: String,
    private val isRtl: Boolean
) : Interceptor {
    override fun intercept(chain: okhttp3.Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val builder = originalRequest.newBuilder()
            .method(originalRequest.method, originalRequest.body)
        // Add Accept-Language header with user preference
        builder.header("Accept-Language", languageCode)
        // Add Direction header for RTL languages
        if (isRtl) {
            builder.header("Direction", "rtl")
        }
        chain.proceed(builder.build())
    }
}