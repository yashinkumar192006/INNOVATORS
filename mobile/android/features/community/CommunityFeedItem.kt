package com.trinetra.mobile.android.features.community

import android.net.Uri
import com.trinetra.mobile.android.core.media.MatchResult.MatchType
import com.trinetra.mobile.android.core.media.MediaMetadataExtractor.Metadata
import com.trinetra.mobile.android.core.media.MediaGuard
import com.trinetra.mobile.android.core.media.MediaGuard.MatchType
import com.trinetra.mobile.android.core.media.investigationHistory.InvestigationEntry
import java.util.Date

/** Represents a single post/community item in the feed */
data class CommunityPost(
    val id: String,
    val authorId: String,
    val authorName: String,
    val timestamp: Long,
    val investigationId: String?,
    val contentType: String?, /** "image", "audio", "video", "text" */
    val contentUri: Uri?, /** URI to the media content */
    val evidenceSummary: String?, /** Brief evidence description */
    val riskClassification: String, /** "LOW", "MEDIUM", "HIGH", "CRITICAL" */
    val communityVotes: Int, /** Persisted backend votes */
    val reviewerAssessment: String?, /** Reviewer's professional assessment */
    val moderatorVerification: Boolean, /** Has moderator verified */
    val independentVerification: Boolean, /** Independently verified findings */
    val matchType: MatchType?, /** Exact or approximate match */
    val comments: List<CommunityComment>?,
    val investigationResult: MatchResult?, /** Associated investigation result */
    val metadata: Metadata? /** Extracted media metadata */
)

/** A comment on a community post */
data class CommunityComment(
    val id: String,
    val userId: String,
    val userName: String,
    val text: String,
    val timestamp: Long,
    val replyTo: String? /** ID of parent comment if this is a reply */
)

/** Vote type for community engagement */
enum class VoteType {
    UPVOTE,
    DOWNVOTE,
    NEUTRAL
}

/** Represents a vote cast by a user */
data class CommunityVote(
    val postId: String,
    val voteType: VoteType,
    val voterId: String,
    val timestamp: Long
)