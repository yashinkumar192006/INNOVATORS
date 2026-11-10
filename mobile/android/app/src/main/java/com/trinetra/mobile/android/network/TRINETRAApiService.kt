package com.trinetra.mobile.android.network

import com.trinetra.mobile.android.network.model.ApiResponse
import com.trinetra.mobile.android.core.media.MediaMetadataExtractor.Metadata
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.Query
import okhttp3.MultipartFile
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File

// API Service interface with exact endpoint paths from openapi.yaml
interface TRINETRAApiService {

    // Health check endpoint: GET /health
    @GET("/health")
    suspend fun healthCheck(): ApiResponse<HealthResponse>

    // Login endpoint: POST /auth/login
    @POST("/auth/login")
    suspend fun login(
        @Body credentials: LoginCredentials
    ): ApiResponse<AuthResponse>

    // Register endpoint: POST /auth/register (inferred from Phase 2)
    @POST("/auth/register")
    suspend fun register(
        @Body credentials: RegisterCredentials
    ): ApiResponse<AuthResponse>

    // Get users endpoint: GET /users
    @GET("/users")
    suspend fun getUsers(
        @Header("Authorization") authToken: String
    ): ApiResponse<UserResponse>

    // Investigation creation (inferred from Phase 9)
    @POST("/investigations")
    suspend fun createInvestigation(
        @Header("Authorization") authToken: String,
        @Body investigation: InvestigationRequest
    ): ApiResponse<InvestigationResponse>

    // Evidence submission (inferred from Phase 9)
    @POST("/investigations/{investigationId}/evidence")
    suspend fun submitEvidence(
        @Path("investigationId") investigationId: String,
        @Body evidence: EvidenceSubmission
    ): ApiResponse<EvidenceResponse>

    // Vote on community post (inferred from Phase 9)
    @POST("/community/posts/{postId}/vote")
    suspend fun castVote(
        @Path("postId") postId: String,
        @Header("Idempotency-Key") idempotencyKey: String,
        @Body vote: VoteRequest
    ): ApiResponse<VoteResponse>

    // Add comment (inferred from Phase 9)
    @POST("/community/posts/{postId}/comments")
    suspend fun addComment(
        @Path("postId") postId: String,
        @Body comment: CommentRequest
    ): ApiResponse<CommentResponse>

    // Get notifications (inferred from Phase 9)
    @GET("/notifications")
    suspend fun getNotifications(
        @Header("Authorization") authToken: String
    ): ApiResponse<NotificationListResponse>

    // Media upload endpoint
    @POST("/upload/media")
    suspend fun uploadMedia(
        @Header("Accept-Language") acceptLanguage: String,
        @Body multipartFile: MultipartFile
    ): ApiResponse<MediaUploadResponse>
}