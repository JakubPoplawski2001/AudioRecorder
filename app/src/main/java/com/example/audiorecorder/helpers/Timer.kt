package com.example.audiorecorder.helpers

import android.os.Handler
import android.os.Looper

class Timer {

    private val handler = Handler(Looper.getMainLooper())
    private var startTime: Long = 0
    private var elapsedTime: Long = 0
    private var isRunning = false

    fun start(onTick: (Long) -> Unit) {
        if (isRunning) return

        startTime = System.currentTimeMillis()
        isRunning = true

        handler.post(object : Runnable {
            val callbackDelayInMSec: Long = 1000

            override fun run() {
                if (isRunning) {
                    elapsedTime = System.currentTimeMillis() - startTime
                    onTick(elapsedTime)
                    handler.postDelayed(this, callbackDelayInMSec)
                }
            }
        })
    }

    fun pause(){

    }

    fun stop() {
        isRunning = false
        handler.removeCallbacksAndMessages(null)
    }
}