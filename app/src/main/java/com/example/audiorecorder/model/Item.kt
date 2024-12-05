package com.example.audiorecorder.model

import java.util.Date
import java.util.UUID

data class Item (
    val id: UUID = UUID.randomUUID(),
    var title: String = "",
    var description: String = "",
    var createDate: Date = Date(),
    var timeLength: Int = 0
)
