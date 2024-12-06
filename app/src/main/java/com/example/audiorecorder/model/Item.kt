package com.example.audiorecorder.model

import java.util.Date
import java.util.UUID

data class Item (
    val id: UUID = UUID.randomUUID(),
    var name: String = "",
    var audioFilePath: String = "",
    var createDate: Date = Date(),
    var timeLength: Int = 0
)
