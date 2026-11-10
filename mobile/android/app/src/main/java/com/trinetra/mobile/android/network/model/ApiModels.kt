package com.trinetra.mobile.android.network.model

// Base API response model with RFC 7807 error handling support
data class ApiResponse<T>(
    val success: Boolean,
    val data: T?,
    val error: ApiError?,
    val metadata: Map<String, String>? = null
)

// RFC 7807 Problem Details error object
data class ApiError(
    val title: String,
    val detail: String?,
    val status: Int,
    val instance: String? = null,
    val correlationId: String? = null
)

// Health check response
data class HealthResponse(
    val status: String,
    val timestamp: String,
    val version: String
)

// Login credentials
data class LoginCredentials(
    val username: String,
    val password: String
)

// Auth response with bearer token
data class AuthResponse(
    val token: String,
    val user: UserResponse,
    val expiresIn: Long
)

// User response
data class UserResponse(
    val id: String,
    val name: String,
    val email: String,
    val role: String?
)

// Registration credentials
data class RegisterCredentials(
    val username: String,
    val email: String,
    val password: String
)

// Investigation request
data class InvestigationRequest(
    val title: String,
    val description: String?,
    val category: String,
    val mediaUris: List<String>?,
    val metadata: Metadata?
)

// Evidence submission
data class EvidenceSubmission(
    val description: String,
    val mediaUri: String,
    val fingerprint: String?
)

// Evidence response
data class EvidenceResponse(
    val evidenceId: String,
    val fingerprint: String,
    val verified: Boolean,
    val timestamp: Long
)

// Vote request
data class VoteRequest(
    val voteType: String, // "UPVOTE" or "DOWNVOTE"
    val justification: String?
)

// Vote response
data class VoteResponse(
    val voteId: String,
    val newVoteCount: Int,
   , val isOwnVote: Boolean
)

// Comment request
data class CommentRequest(
    val text: String,
    val parentCommentId: String?
)

// Comment response
data class CommentResponse(
    val commentId: String,
    val author: String,
    val text: String,
    val createdAt: Long
)

// Notification list response
data class NotificationListResponse(
    val notifications: List<NotificationItem>,
    val hasMore: Boolean
)

// Notification item
data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val timestamp: Long,
    val type: String,
    val relatedId: String?
)

// Media upload response
data class MediaUploadResponse(
    val mediaId: String,
    val url: String,
    val status: String,
    val thumbnailUrl: String?
)

// User profile update request (inferred)
data class ProfileUpdateRequest(
    val fullName: String?,
    val email: String?,
    val currentPassword: String?,
    val newPassword: String?
)