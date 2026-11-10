package com.trinetra.mobile.android.core.network.api

import com.trinetra.mobile.android.core.network.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Body

interface TRINETRAApiService {

    @GET("/health")
    suspend fun healthCheck(): ApiResponse<HealthResponse>

    @GET("/users")
    suspend fun getUsers(
        @Header("Authorization") authToken: String
    ): ApiResponse<UserResponse>

    @POST("/auth/login")
    suspend fun login(
        @Body credentials: LoginCredentials
    ): ApiResponse<AuthResponse>

    @POST("/auth/register")
    suspend fun register(
        @Body credentials: RegisterCredentials
    ): ApiResponse<AuthResponse>
}