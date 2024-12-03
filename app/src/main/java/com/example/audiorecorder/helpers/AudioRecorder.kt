package com.example.audiorecorder.helpers

import android.content.Context
import android.media.MediaRecorder
import java.io.File

class AudioRecorder (
    private val context: Context,
    private val file: File
) {

    private var recorder: MediaRecorder = MediaRecorder()

    init {
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        recorder.setOutputFile(file)

        recorder.prepare()
    }

    fun start(){
        recorder.start()
    }

    fun pause() {
        recorder.pause()
    }

    fun stop(){
        recorder.stop()
        recorder.reset()
    }

}