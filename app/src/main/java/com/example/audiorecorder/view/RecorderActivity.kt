package com.example.audiorecorder.view

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.audiorecorder.R
import com.example.audiorecorder.helpers.Recorder
import com.example.audiorecorder.helpers.TimeUtils
import com.example.audiorecorder.helpers.Timer
import java.io.File

class RecorderActivity : AppCompatActivity() {

    private lateinit var recorder: Recorder
    private lateinit var timer: Timer

    private lateinit var toolBar: Toolbar
    private lateinit var timeLabel: TextView
    private lateinit var startStopButton: Button
    private lateinit var pauseButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recorder)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        recorder = Recorder(this, File(cacheDir, "test.mp3"))
        timer = Timer(1000)

        // Setup ToolBar
        toolBar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolBar.setNavigationOnClickListener {
            finish()
        }

        timeLabel = findViewById(R.id.timeLabel)
        startStopButton = findViewById(R.id.startStopButton)
        pauseButton = findViewById(R.id.pauseButton)


        startStopButton.setOnClickListener {
            if (!recorder.hasRecordingStarted) {
                startRecording()
                // todo: Update UI btn
            } else {
                stopRecording()
                // todo: Update UI btn
            }
        }

        pauseButton.setOnClickListener {
            if (!recorder.hasRecordingStarted) return@setOnClickListener

            if (!recorder.isPaused) {
                pauseRecording()
                // todo: Update UI btn
            } else {
                resumeRecording()
                // todo: Update UI btn
            }
        }

    }

    private fun startRecording() {
        startTimer()
        recorder.start()
    }

    private fun stopRecording() {
        timer.stop()
        recorder.stop()

        // todo: save recording prompt
        finish()
    }

    private fun pauseRecording() {
        timer.pause()
        recorder.pause()
    }

    private fun resumeRecording() {
        startTimer()
        recorder.resume()
    }

    private fun startTimer() {
        timer.start { elapsedTime ->
            timeLabel.text = TimeUtils.toString(elapsedTime.toInt(),
                TimeUtils.FormatStyle.HOURS_MINUTES_SECONDS)
        }
    }

}