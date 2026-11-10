package com.trinetra.mobile.android.core.network.model

// Base API response model
data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val error: String?
)

// Health check response
data class HealthResponse(
    val status: String,
    val timestamp: String
)

// User response
data class UserResponse(
    val id: String,
    val name: String,
    val email: String
)

// Login credentials
data class LoginCredentials(
    val username: String,
    val password: String
)

// Registration credentials
data class RegisterCredentials(
    val username: String,
    val email: String,
    val password: String
)

// Authentication response
data class AuthResponse(
    val token: String,
    val user: UserResponse,
    val expiresIn: Long
)