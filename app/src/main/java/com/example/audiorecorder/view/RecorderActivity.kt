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
    private lateinit var playButton: Button
    private lateinit var stopButton: Button

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
        playButton = findViewById(R.id.playButton)
        stopButton = findViewById(R.id.stopButton)


        playButton.setOnClickListener {
            if (!recorder.isRecording) {
                startRecording()
                // Update UI btn
            } else {
                pauseRecording()
                // Update UI btn
            }
        }

        stopButton.setOnClickListener { stopRecording() }

    }

    private fun startRecording() {
        timer.start { elapsedTime ->
            timeLabel.text = TimeUtils.toString(elapsedTime.toInt(),
                TimeUtils.FormatStyle.HOURS_MINUTES_SECONDS)
        }

        recorder.start()
    }

    private fun pauseRecording() {
        timer.pause()

        recorder.stop()
    }

    private fun stopRecording() {
        timer.stop()

        recorder.stop()
    }
}