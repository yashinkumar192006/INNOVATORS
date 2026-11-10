package com.trinetra.mobile.android.results

data class HistoricalMatch(
    val investigationId: String,
    val matchType: ResultType.MatchType,
    val similarityScore: Float?,
    val timestamp: Long,
    val verificationStatus: String?)