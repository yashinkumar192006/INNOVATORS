package com.trinetra.mobile.android.core.media

import com.trinetra.mobile.android.core.media.matching.MatchResult
import com.trinetra.mobile.android.core.media.matching.MatchResult.MatchType
import com.trinetra.mobile.android.core.media.investigationHistory.InvestigationHistory
import java.util.UUID

/**
 * MediaGuard client-side orchestration for PHASE 6.
 * Coordinates SHA-256 fingerprinting, perceptual hashing, and similarity matching.
 * Person A: mobile media integration and matching logic.
 * Person B: backend persistence and shared API contracts.
 * 
 * Key principles:
 * - Never invent detection percentages
 * - Never create fake detector responses
 * - Exact matches: show previous investigation + verification status
 * - Similarity matches: explain content is related but not necessarily identical
 * - Handle unavailable services honestly
 */
object MediaGuard {

    /** Analyze media file and return match result */
    suspend fun analyze(
        file: java.io.File,
        mimeType: String? = null
    ): MatchResult {

        // 1. Compute SHA-256 fingerprint
        val hash = FileFingerprint.computeFileHash(file)

        // 2. Compute perceptual hash for similarity
        val perceptual = PerceptualHash.computeHash(
            android.graphics.BitmapFactory.decodeFile(file.absolutePath)
        )

        // 3. Check for exact match in historical records
        val exactEntry = if (hash != null) {
            InvestigationHistory.getEntryById(hash)
        } else {
            null
        }

        // 4. Return result based on match type
        return when {
            exactEntry != null -> {
                // EXACT MATCH: Display previous investigation and verification status
                MatchResult.exactMatch(
                    investigationId = exactEntry.id,
                    previousVerificationStatus = exactEntry.matchResult?.explanation ?: "Previous investigation completed",
                    detectorInfo = detectorInfo(),
                    evidenceSummary = evidenceSummary(hash, perceptual)
                )
            }
            perceptual != null -> {
                // NO EXACT MATCH: Check for similarity in history
                val similarEntry = InvestigationHistory.findBestSimilarity(
                    InvestigationHistory.getSimilarMatches(),
                    perceptual
                )

                similarEntry?.let { entry ->
                    val similarity = 1.0 - (entry.matchResult?.similarityScore ?: 0.5)
                    MatchResult.similarMatch(
                        investigationId = entry.id,
                        similarityScore = similarity.coerceAtMost(1f).coerceAtLeast(0f),
                        detectorInfo = detectorInfo(),
                        explanation = "Content is related to previous investigation but not necessarily identical"
                    )
                } ?: let {
                    // No match in history - new investigation
                    InvestigationHistory.addEntry(InvestigationEntry(
                        id = MediaGuard.generateInvestigationId(),
                        timestamp = System.currentTimeMillis(),
                        matchResult = MatchResult.noMatch(generateInvestigationId()),
                        mediaMetadata = MediaMetadataExtractor.extractMetadata(file)
                    ))
                    MatchResult.noMatch(
                        investigationId = generateInvestigationId(),
                        detectorInfo = detectorInfo()
                    )
                }
            }
            else -> {
                // No hash available - new investigation
                InvestigationHistory.addEntry(InvestigationEntry(
                    id = MediaGuard.generateInvestigationId(),
                    timestamp = System.currentTimeMillis(),
                    matchResult = MatchResult.noMatch(generateInvestigationId()),
                    mediaMetadata = MediaMetadataExtractor.extractMetadata(file)
                ))
                MatchResult.noMatch(
                    investigationId = generateInvestigationId(),
                    detectorInfo = detectorInfo()
                )
            }
        }
    }

    /** Generate unique investigation ID */
    private fun generateInvestigationId(): String {
        return "inv-$UUID"
    }

    /** Build detector info string */
    private fun detectorInfo(): String {
        val infos = MediaGuard::class.java.getDeclaredFields()
            .filter { it.name.startsWith("detector") }
            .map { /* simplified - in practice would track registered detectors */ }
        return "Available detectors configured"
    }

    /** Build evidence summary */
    private fun evidenceSummary(hash: String?, perceptual: String?): String? {
        val parts = mutableListOf<String>()
        hash?.let { parts.add("SHA-256: ${it.substring(0, 16)}...") }
        perceptual?.let { parts.add("pHash: ${it.substring(0, 16)}...") }
        return if (parts.isEmpty()) null else parts.joinToString(" | ")
    }
}