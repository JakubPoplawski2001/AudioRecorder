package com.example.audiorecorder.helpers

object TimeUtils {

    fun toString(timeInMSec: Int, formatStyle: FormatStyle): String{
        val milliseconds = timeInMSec % 1000
        val seconds = timeInMSec / 1000 % 60
        val minutes = timeInMSec / (1000 * 60) % 60
        val hours = timeInMSec / (1000 * 60 * 60)

        return when (formatStyle) {
            FormatStyle.HOURS_MINUTES_SECONDS_MILLIS ->
                String.format("%02d:%02d:%02d.%03d", hours, minutes, seconds, milliseconds)
            FormatStyle.HOURS_MINUTES_SECONDS ->
                String.format("%02d:%02d:%02d", hours, minutes, seconds)
            FormatStyle.MINUTES_SECONDS ->
                String.format("%02d:%02d", minutes, seconds)
            FormatStyle.MINUTES_SECONDS_MILLIS ->
                String.format("%02d:%02d.%03d", minutes, seconds, milliseconds)
        }
    }

    // Enum class for format styles
    enum class FormatStyle {
        HOURS_MINUTES_SECONDS_MILLIS, // Format as hh:mm:ss.mmm
        HOURS_MINUTES_SECONDS,        // Format as hh:mm:ss
        MINUTES_SECONDS,              // Format as mm:ss
        MINUTES_SECONDS_MILLIS        // Format as mm:ss.mmm
    }
}