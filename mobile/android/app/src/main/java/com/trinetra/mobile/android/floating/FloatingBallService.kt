package com.trinetra.mobile.android.floating

import android.app.Service
import android.content.Intent
import android.graphics.PixelFormat
import android.os.IBinder
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager

// PHASE 3: Floating i-Ball assistant
// Circular floating security button, draggable, snap to edges
// Three primary options: MEDIA, INFORMATION, IDENTITY

class FloatingBallService : Service() {

    private var windowManager: WindowManager? = null
    private var floatingView: View? = null
    private var params: WindowManager.LayoutParams? = null

    // State machine: COLLAPSED, EXPANDED, INVESTIGATING
    private enum class BallState { COLLAPSED, EXPANDED, INVESTIGATING }
    private var currentState = BallState.COLLAPSED

    // Exactly 3 options - never 4 per PHASE 3 spec
    sealed class InvestigationOption {
        object MEDIA -> MEDIA
        object INFORMATION -> INFORMATION
        object IDENTITY -> IDENTITY
    }

    override fun onCreate() {
        super.onCreate()
        // Permission check handled in onStartCommand
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        createCollapsedView()
    }

    private fun createCollapsedView() {
        floatingView = LayoutInflater.from(this).inflate(R.layout.floating_ball_collapsed, null)

        floatingView?.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    params?.apply { x = event.rawX - params.width / 2; y = event.rawY - params.height / 2 }
                }
                MotionEvent.ACTION_MOVE -> {
                    params?.apply {
                        x = Math.max(0, Math.min(event.rawX - params.width / 2, rootViewWidth - params.width))
                        y = Math.max(0, Math.min(event.rawY - params.height / 2, rootViewHeight - params.height))
                    }
                }
            }
            true
        }

        floatingView?.setOnClickListener { toggleState() }
        windowManager?.addView(floatingView, params!!)
    }

    private fun toggleState() {
        when (currentState) {
            BallState.COLLAPSED -> currentState = BallState.EXPANDED
            BallState.EXPANDED -> currentState = BallState.COLLAPSED
        }
        updateView()
    }

    private fun selectOption(option: InvestigationOption) {
        currentState = BallState.INVESTIGATING
        // Map to backend: MEDIA->MEDIA, INFORMATION->INFORMATION, IDENTITY->IDENTITY
        currentState = BallState.COLLAPSED
        updateView()
    }

    private fun updateView() {
        floatingView?.let { view ->
            when (currentState) {
                BallState.COLLAPSED -> windowManager?.updateViewLayout(view, params!!)
                BallState.EXPANDED -> windowManager?.updateViewLayout(view, expandParams())
            }
        }
    }

    private fun expandParams(): WindowManager.LayoutParams {
        val p = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        p.gravity = android.view.Gravity.CENTER
        return p
    }

    private var rootViewWidth = 0
    private var rootViewHeight = 0

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                params?.x = event.rawX - rootViewWidth / 2
                params?.y = event.rawY - rootViewHeight / 2
            }
            MotionEvent.ACTION_MOVE -> {
                params?.apply {
                    x = Math.max(0, Math.min(event.rawX - rootViewWidth / 2, rootViewWidth - rootViewWidth))
                    y = Math.max(0, Math.min(event.rawY - rootViewHeight / 2, rootViewHeight - rootViewHeight))
                }
                // Snap to edges
                if (y < rootViewHeight / 2) y = 0
                else y = rootViewHeight - rootViewHeight
            }
        }
        true
    }

    private fun hasOverlayPermission(): Boolean =
        android.provider.Settings.canDrawOverlays(this)

    private fun requestOverlayPermission() {
        startActivity(android.provider.Settings.ACTION_MANAGE_OVERLAY_PERMISSION(
            android.net.Uri.parse("package:$packageName")))
    }

    override fun onDestroy() {
        super.onDestroy()
        floatingView?.let { windowManager?.removeView(it) }
    }

    @Override
    override fun onBind(intent: Intent): IBinder? = null
}