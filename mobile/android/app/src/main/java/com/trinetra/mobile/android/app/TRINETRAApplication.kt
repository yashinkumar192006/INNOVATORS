package com.trinetra.mobile.android.app

import android.app.Application
import androidx.hilt.lifecycle.HiltAndroidApp

@HiltAndroidApp
class TRINETRAApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Application initialization
        // TODO: Initialize dependency injection, logging, etc.
    }
}