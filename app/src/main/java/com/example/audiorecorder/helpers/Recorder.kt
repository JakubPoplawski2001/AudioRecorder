package com.example.audiorecorder.helpers

import android.content.Context
import android.media.MediaRecorder
import java.io.File

class Recorder (
    private val context: Context,
    private val file: File
) {
    private val audioRecorder: AudioRecorder = AudioRecorder(context, file)
    var isRecording: Boolean = false

    fun start(){
        isRecording = true
        audioRecorder.start()
    }

    fun pause() {
        isRecording = false
        audioRecorder.pause()
    }

    fun stop(){
        isRecording = false
        audioRecorder.stop()
    }
}