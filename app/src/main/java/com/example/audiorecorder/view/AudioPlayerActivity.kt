package com.example.audiorecorder.view

import android.os.Bundle
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.audiorecorder.R
import com.example.audiorecorder.helpers.AudioPlayer
import com.example.audiorecorder.helpers.SeekBarProgressHandler
import com.example.audiorecorder.helpers.TimeUtils
import com.example.audiorecorder.model.Item
import java.io.File
import java.util.UUID

class AudioPlayerActivity : AppCompatActivity() {
    var itemId: UUID? = null
    private var item: Item? = null
    private lateinit var file: File

    private lateinit var player: AudioPlayer
    private lateinit var seekBarProgressHandler: SeekBarProgressHandler

    private lateinit var toolBar: Toolbar
    private lateinit var currentTimeLabel: TextView
    private lateinit var endTimeLabel: TextView
    private lateinit var seekBar: SeekBar
    private lateinit var stopButton: Button
    private lateinit var playButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_audio_player)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get item from database
//        item = getItemById(itemId)

        file = File(cacheDir, "test.mp3")

        player = AudioPlayer(this, file)

        // Setup ToolBar
        toolBar = findViewById(R.id.toolbar)
        setSupportActionBar(toolBar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolBar.setNavigationOnClickListener {
            finish()
        }

        currentTimeLabel = findViewById(R.id.currentTimeLabel)
        endTimeLabel = findViewById(R.id.endTimeLabel)
        stopButton = findViewById(R.id.stopButton)
        playButton = findViewById(R.id.playButton)

        endTimeLabel.text = TimeUtils.toString(
            player.getDuration(),
            TimeUtils.FormatStyle.HOURS_MINUTES_SECONDS_MILLIS
        )

        playButton.setOnClickListener {
            if (!player.isPlaying) {
                startPlaying()
                // todo: update UI btn
            } else {
                pausePlaying()
                // todo: update UI btn
            }
        }

        stopButton.setOnClickListener {
            stopPlaying()
        }

        // Setup SeekBar
        seekBar = findViewById(R.id.seekBar)
        seekBar.max = player.getDuration()

        seekBarProgressHandler = SeekBarProgressHandler(100)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    player.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                seekBarProgressHandler.stop()
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                player.seekTo(seekBar?.progress ?: 0)
                if (player.isPlaying) {
                    StartSeekBarProgressUpdate()
                }
            }
        })

    }

    override fun onDestroy() {
        super.onDestroy()
        seekBarProgressHandler.stop()
        player.release()
    }

    override fun onPause() {
        super.onPause()
        pausePlaying()
    }

    private fun startPlaying() {
        player.start()
        StartSeekBarProgressUpdate()
    }

    private fun pausePlaying() {
        player.pause()
        seekBarProgressHandler.stop()
    }

    private fun stopPlaying() {
        player.stop()
        seekBarProgressHandler.stop()
    }

    private fun StartSeekBarProgressUpdate() {
        seekBarProgressHandler.start {
            val time = player.getCurrentPosition()
            seekBar.progress = time
            currentTimeLabel. text = TimeUtils.toString(
                time,
                TimeUtils.FormatStyle.HOURS_MINUTES_SECONDS_MILLIS)
        }
    }
}