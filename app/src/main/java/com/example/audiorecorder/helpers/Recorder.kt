package com.example.audiorecorder.helpers

import android.content.Context
import java.io.File

class Recorder (
    private val context: Context,
    private val file: File
) {
    private val audioRecorder: AudioRecorder = AudioRecorder(context, file)
    var hasRecordingStarted: Boolean = false
    var isPaused: Boolean = false

    fun start(){
        hasRecordingStarted = true
        audioRecorder.start()
    }

    fun stop(){
        hasRecordingStarted = false
        audioRecorder.stop()
    }

    fun pause() {
        isPaused = true
        audioRecorder.pause()
    }

    fun resume() {
        isPaused = false
        audioRecorder.resume()
    }
}