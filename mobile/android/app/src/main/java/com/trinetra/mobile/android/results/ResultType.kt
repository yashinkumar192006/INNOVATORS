package com.trinetra.mobile.android.results

enum class ResultType(
    val displayName: String
) {
    VERIFIED_MATCH("Verified Match"),
    SIMILAR_HISTORICAL_CONTENT("Similar Historical Content"),
    NEW_INVESTIGATION("New Investigation"),
    INSUFFICIENT_EVIDENCE("Insufficient Evidence"),
    ANALYSIS_UNAVAILABLE("Analysis Unavailable")
}