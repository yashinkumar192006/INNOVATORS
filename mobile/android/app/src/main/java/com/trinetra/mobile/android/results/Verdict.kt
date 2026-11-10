package com.trinetra.mobile.android.results

enum class Verdict(
    val displayName: String
) {
    CLEAN("Clean"),
    SUSPICIOUS("Suspicious"),
    THREAT("Threat"),
    UNKNOWN("Unknown"),
    CONFIRMED_THREAT("Confirmed Threat")
}