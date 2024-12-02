package com.example.audiorecorder.helpers

import android.content.Context
import android.media.MediaRecorder
import java.io.File

class AudioRecorder() {

    private var recorder: MediaRecorder = MediaRecorder()

    fun start(outputFile: File){
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC)
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
        recorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
        recorder.setOutputFile(outputFile)

        recorder.prepare()
        recorder.start()
    }

    fun stop(){
        recorder.stop()
        recorder.reset()
    }

}