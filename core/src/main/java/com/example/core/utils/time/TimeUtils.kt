package com.example.core.utils.time

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

object TimeUtils {
    fun formatTimestamp(input: String): String {
        return try {
            // Parse the input timestamp with timezone
            val dateTime = OffsetDateTime.parse(input.replace(" ", "Z")) // Adjust timezone format
            // Format without microseconds and timezone
            dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        } catch (e: Exception) {
            input // Return original if parsing fails
        }
    }
}

