package com.example.audiorecorder.helpers

import android.os.Handler
import android.os.Looper

class Timer (private val callbackDelayInMSec: Long = 300 ) {

    private val handler = Handler(Looper.getMainLooper())
    private var startTime: Long = 0
    private var recordedTime: Long = 0
    private var isRunning = false

    fun start(onTick: (Long) -> Unit) {
        if (isRunning) return

        startTime = System.currentTimeMillis()
        isRunning = true

        handler.post(object : Runnable {
            override fun run() {
                if (!isRunning) return

                val elapsedTime = System.currentTimeMillis() - startTime + recordedTime
                onTick(elapsedTime)
                handler.postDelayed(this, callbackDelayInMSec)
            }
        })
    }

    fun pause() {
        if (!isRunning) return

        recordedTime += System.currentTimeMillis() - startTime
        isRunning = false
        handler.removeCallbacksAndMessages(null)
    }

    fun stop(): Int {
        if (isRunning) recordedTime += System.currentTimeMillis() - startTime

        isRunning = false
        handler.removeCallbacksAndMessages(null)
        val duration = recordedTime.toInt()
        recordedTime = 0
        return duration
    }

    fun getRecordedTime(): Int {
        return recordedTime.toInt()
    }
}