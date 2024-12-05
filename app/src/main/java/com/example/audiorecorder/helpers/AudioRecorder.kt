package com.example.audiorecorder.helpers

import android.content.Context
import android.media.MediaRecorder
import android.os.Build
import java.io.File

class AudioRecorder (
        private val context: Context,
        private val file: File
    ) {

        private var recorder: MediaRecorder

        init {
            recorder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                MediaRecorder(context)
            } else {
                MediaRecorder()
            }

            recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
            recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            recorder.setOutputFile(file)

            recorder.prepare()
        }

    fun start(){
        recorder.start()
    }

    fun stop(){
        recorder.stop()
        recorder.reset()
    }

    fun pause() {
        recorder.pause()
    }

    fun resume(){
        recorder.resume()
    }

}