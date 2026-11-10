package com.trinetra.mobile.android.results

data class ProcessingState(
    val state: String,
    val progress: Float?,
    val message: String?
)