package com.example.audiorecorder.model

import java.util.Date

data class Item (
    val title: String = "",
    val description: String = "",
    val createDate: Date = Date(),
    val timeLength: Int = 0
)
