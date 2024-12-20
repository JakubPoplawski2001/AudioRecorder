package com.example.audiorecorder.view

import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.audiorecorder.R
import com.example.audiorecorder.helpers.Recorder
import com.example.audiorecorder.helpers.SaveDialog
import com.example.audiorecorder.helpers.TimeUtils
import com.example.audiorecorder.helpers.Timer
import com.example.audiorecorder.model.Database
import com.example.audiorecorder.model.Item
import java.io.File
import java.nio.file.Files

class RecorderActivity : AppCompatActivity() {

    private lateinit var database: Database
    private lateinit var file: File
    private lateinit var recorder: Recorder
    private lateinit var timer: Timer

    private lateinit var toolBar: Toolbar
    private lateinit var timeLabel: TextView
    private lateinit var startStopButton: ImageButton
    private lateinit var pauseButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_recorder)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        database = Database.getInstance(this)
        file = File(cacheDir, "tmp.mp3")

        recorder = Recorder(this, file)
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
            } else {
                stopRecording()
            }
        }

        pauseButton.setOnClickListener {
            if (!recorder.hasRecordingStarted) return@setOnClickListener

            if (!recorder.isPaused) {
                pauseRecording()
            } else {
                resumeRecording()
            }
        }

    }



    private fun saveDialog(duration: Int) {
        val dialog = SaveDialog(this,
            onValidateInput = { name ->
                if (name.isNullOrBlank()) return@SaveDialog SaveDialog.NULL_OR_BLANK
                if (!saveAudioFile(name)) return@SaveDialog SaveDialog.FILE_ALREADY_EXIST
                Toast.LENGTH_LONG

                return@SaveDialog SaveDialog.SUCCESS
            },
            onSave = { name ->
                val item = Item()
                item.name = name
                item.timeLength = duration
                item.audioFilePath = file.path

                database.addItem(item)
                finish()
            },
            onCancel = {
                finish()
            })
            .show()
    }

    private fun saveAudioFile(newName: String, extension: String = "mp3"): Boolean {
        val newFile = File(applicationContext.filesDir, "$newName.$extension")
        if (newFile.exists()) {
            return false
        }
        val newPath = Files.move(file.toPath(), newFile.toPath())
        file = File(newPath.toUri())
        return true
    }

    private fun startRecording() {
        startTimer()
        recorder.start()
        startStopButton.setImageDrawable(getDrawable(R.drawable.baseline_stop_24))
    }

    private fun stopRecording() {
        val duration = timer.stop()
        recorder.stop()
        startStopButton.setImageDrawable(getDrawable(R.drawable.baseline_fiber_manual_record_24))

        saveDialog(duration)
    }

    private fun pauseRecording() {
        timer.pause()
        recorder.pause()
        pauseButton.setImageDrawable(getDrawable(R.drawable.baseline_play_arrow_24))

    }

    private fun resumeRecording() {
        startTimer()
        recorder.resume()
        pauseButton.setImageDrawable(getDrawable(R.drawable.baseline_pause_24))
    }

    private fun startTimer() {
        timer.start { elapsedTime ->
            timeLabel.text = TimeUtils.toString(elapsedTime.toInt(),
                TimeUtils.FormatStyle.HOURS_MINUTES_SECONDS)
        }
    }

}