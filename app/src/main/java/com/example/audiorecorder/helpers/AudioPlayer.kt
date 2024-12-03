package com.example.audiorecorder.helpers

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import java.io.File

class AudioPlayer (
    private val context: Context,
    private val file: File
) {

    private var player : MediaPlayer
    var isPlaying : Boolean = false

    init {
        MediaPlayer.create(context, file.toUri()).apply {
            player = this
//            player.prepare() // no need to call. .create(...) does that
            player.isLooping = false
        }
    }

    fun start() {
        isPlaying = true
        player.start()
    }

    fun pause() {
        isPlaying = false
        player.pause()
    }

    fun stop() {
        isPlaying = false
//        player.stop() // after stop need to prepare player before starting player again
        seekTo(0)
    }

    fun seekTo(position: Int) {
        player.seekTo(position)
    }

    fun getDuration(): Int {
        return player.duration ?: 0
    }

    protected fun finalize() {
        // overrides basic java Object finalize method
        release()
    }

    fun release() {
        player.release()
    }

}