package com.trinetra.mobile.android.results

data class InvestigationResult(
    val investigationId: String,
    val investigationCategory: String,
    val contentSummary: String,
    val processingState: ProcessingState,
    val riskClassification: RiskClassification,
    val verdict: Verdict,
    val confidence: Float?,
    val evidenceFindings: String?,
    val provenanceFindings: String?,
    val historicalMatches: List<HistoricalMatch>?,
    val sources: List<String>?,
    val detectorInformation: String?,
    val uncertaintyExplanation: String?,
    val recommendedUserAction: String,
    val timestamp: Long,
    val resultType: ResultType
)