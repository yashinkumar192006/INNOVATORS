package com.trinetra.mobile.android.core.media

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log

/**
 * Investigation history display and tracking.
 * Person A implementation - local historical display logic.
 * Person B owns backend persistence and API contracts.
 */
object InvestigationHistory {

    private const val MAX_HISTORY_ENTRIES = 50
    private var historyList = mutableListOf<InvestigationEntry>()

    data class InvestigationEntry(
        val id: String,
        val timestamp: Long,
        val matchResult: MatchResult?,
        val mediaMetadata: MediaMetadataExtractor.Metadata?,
        val detectorNames: List<String> = emptyList(),
        val detectorVersions: List<String> = emptyList()
    )

    /** Add a new investigation entry to history */
    fun addEntry(entry: InvestigationEntry) {
        if (historyList.size >= MAX_HISTORY_ENTRIES) {
            historyList.removeAt(0)
        }
        historyList.add(entry)
    }

    /** Get the full investigation history */
    fun getHistory(): List<InvestigationEntry> {
        return historyList
    }

    /** Get the most recent investigation entry */
    fun getLatestEntry(): InvestigationEntry? {
        return historyList.lastOrNull()
    }

    /** Get investigation entry by ID */
    fun getEntryById(id: String): InvestigationEntry? {
        return historyList.find { it.id == id }
    }

    /** Get exact match entries from history */
    fun getExactMatches(): List<InvestigationEntry> {
        return historyList.filter { entry ->
            entry.matchResult?.matchType == MatchResult.MatchType.EXACT
        }
    }

    /** Get similarity match entries from history */
    fun getSimilarMatches(): List<InvestigationEntry> {
        return historyList.filter { entry ->
            entry.matchResult?.matchType == MatchResult.MatchType.SIMILAR
        }
    }

    /** Clear investigation history */
    fun clearHistory() {
        historyList.clear()
    }

    /** Log investigation for debugging */
    fun logInvestigation(entry: InvestigationEntry) {
        Log.d("InvestigationHistory", "Investigation: ${entry.id}, " +
                "match=${entry.matchResult?.matchType}, " +
                "timestamp=${entry.timestamp}, " +
                "fingerprint=${entry.mediaMetadata?.sha256Fingerprint}")
    }
}