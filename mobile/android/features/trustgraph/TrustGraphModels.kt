package com.trinetra.mobile.android.features.trustgraph

import com.trinetra.mobile.android.core.media.MatchResult.MatchType
import com.trinetra.mobile.android.core.media.investigationHistory.InvestigationEntry
import java.util.Date

/** Represents a relationship between two investigation elements */
data class TrustRelationship(
    val sourceId: String,       /** ID of the source element */
    val targetId: String,       /** ID of the target element */
    val relationshipType: String,  /** "claims", "media", "url", "identity" */
    val relationshipStrength: String,  /** "verified", "inferred", "possible" */
    val explanation: String,    /** Why this relationship exists */
    val associatedInvestigation: String?,  /** Investigation ID connecting these elements */
    val timestamp: Long         /** When this relationship was established */
)

/** Represents a node in the trust graph */
data class TrustGraphNode(
    val id: String,
    val type: String,   /** "investigation", "claim", "media", "url", "identity" */
    val label: String,
    val metadata: Map<String, String>?,
    val investigations: List<String>,  /** Investigations involving this node */
    val isVerified: Boolean
)

/** Represents a connected investigation cluster */
data class InvestigationCluster(
    val clusterId: String,
    val centralNode: String,
    val memberNodes: List<String>,
    val sharedEvidence: List<String>,
    val relationshipStrength: String,  /** "strong", "moderate", "weak" */
    val verifiedCount: Int,
    val inferredCount: Int
)

/** TrustGraph display result */
data class TrustGraphResult(
    val nodes: List<TrustGraphNode>,
    val relationships: List<TrustRelationship>,
    val clusters: List<InvestigationCluster>,
    val explanation: String,  /** Overall explanation of the trust graph */
    val lastUpdated: Long
)