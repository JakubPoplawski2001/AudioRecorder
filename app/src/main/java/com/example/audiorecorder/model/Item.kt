package com.example.audiorecorder.model

import java.util.Date
import java.util.UUID

data class Item (
    val id: UUID = UUID.randomUUID(),
    var createDate: Date = Date(),
    var name: String = "",
    var audioFilePath: String = "",
    var timeLength: Int = 0
)
