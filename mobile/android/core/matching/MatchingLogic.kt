package com.trinetra.mobile.android.core.media

import com.trinetra.mobile.android.core.media.PerceptualHash

/**
 * Matching result classification and reasoning.
 * Person A implementation - client-side matching logic.
 * Person B owns backend persistence and API contracts.
 */
data class MatchResult(
    /** Unique investigation identifier */
    val investigationId: String,
    /** Match type determination */
    val matchType: MatchType,
    /** Similarity score (0.0 to 1.0, only meaningful for approximate matches) */
    val similarityScore: Float?,
    /** Human-readable explanation */
    val explanation: String,
    /** Previous investigation verification status (for exact matches) */
    val previousVerificationStatus: String?,
    /** Detector information */
    val detectorInfo: String?,
    /** Evidence summary */
    val evidenceSummary: String?
) {

    enum class MatchType {
        /** Exact hash match - content is identical */
        EXACT,
        /** Approximate similarity - content is related but not verified identical */
        SIMILAR,
        /** No match found */
        NONE
    }

    /** Companion object for creating MatchResult instances */
    companion {
        /** Create MatchResult for exact hash match */
        fun exactMatch(
            investigationId: String,
            previousVerificationStatus: String?,
            detectorInfo: String? = null,
            evidenceSummary: String? = null
        ): MatchResult {
            return MatchResult(
                investigationId = investigationId,
                matchType = MatchType.EXACT,
                similarityScore = null,
                explanation = "Content matches exactly with previous investigation",
                previousVerificationStatus = previousVerificationStatus,
                detectorInfo = detectorInfo,
                evidenceSummary = evidenceSummary
            )
        }

        /** Create MatchResult for similarity match */
        fun similarMatch(
            investigationId: String,
            similarityScore: Float,
            detectorInfo: String? = null,
            explanation: String? = null
        ): MatchResult {
            return MatchResult(
                investigationId = investigationId,
                matchType = MatchType.SIMILAR,
                similarityScore = similarityScore.coerceIn(0f, 1f),
                explanation = explanation ?: "Content is related but not necessarily identical (similarity: ${(similarityScore * 100).toInt()}%)",
                previousVerificationStatus = null,
                detectorInfo = detectorInfo,
                evidenceSummary = null
            )
        }

        /** Create MatchResult for no match */
        fun noMatch(
            investigationId: String,
            detectorInfo: String? = null
        ): MatchResult {
            return MatchResult(
                investigationId = investigationId,
                matchType = MatchType.NONE,
                similarityScore = null,
                explanation = "No matching content found in historical records",
                previousVerificationStatus = null,
                detectorInfo = detectorInfo,
                evidenceSummary = null
            )
        }
    }
}

/**
 * Investigation identifier tracking.
 * Person A implementation - local tracking logic.
 * Person B owns backend persistence.
 */
data class InvestigationIdentifier(
    /** Unique ID generated for this investigation */
    val id: String,
    /** SHA-256 fingerprint of the investigated media */
    val mediaFingerprint: String?,
    /** Perceptual hash of the media content */
    val perceptualHash: String?,
    /** Timestamp of investigation */
    val timestamp: Long,
    /** Match result reference */
    val matchResult: MatchResult?,
    /** Associated metadata */
    val metadata: Map<String, String> = mutableMapOf()
)