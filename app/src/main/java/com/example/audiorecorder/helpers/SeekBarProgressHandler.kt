package com.example.audiorecorder.helpers

import android.os.Handler
import android.os.Looper

class SeekBarProgressHandler (private val callbackDelayInMSec: Long = 300) {
    private val handler = Handler(Looper.getMainLooper())
    private var isRunning = false

    fun start(onTick: () -> Unit) {
        if (isRunning) return
        isRunning = true

        handler.post(object : Runnable {
            override fun run() {
                if (!isRunning) return

                onTick()
                handler.postDelayed(this, callbackDelayInMSec)
            }
        })
    }

    fun stop() {
        isRunning = false
        handler.removeCallbacksAndMessages(null)
    }
}