package com.mahadiks.basketballapp.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.Instant
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun convertToUserLocalTime(date: String, time: String): LocalDateTime {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val combined = "$date $time"
    val utcTime = LocalDateTime.parse(combined, formatter)
    return utcTime.atZone(ZoneId.of("UTC")).withZoneSameInstant(ZoneId.systemDefault()).toLocalDateTime()
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatGameDate(dateString: String): String {
    return try {
        val instant = Instant.parse(dateString)
        val formatter = DateTimeFormatter.ofPattern("EEE MMM dd", Locale.ENGLISH)
            .withZone(ZoneId.systemDefault())
        formatter.format(instant)
    } catch (e: Exception) {
        ""
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatGameYearAndMonth(dateString: String): String {
    return try {
        val instant = Instant.parse(dateString)
        val formatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale.ENGLISH)
            .withZone(ZoneId.systemDefault())
        formatter.format(instant)
    } catch (e: Exception) {
        ""
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun parseTimeString(timeString: String): LocalTime? {
    return try {
        val cleanedTime = timeString.replace(" ET", "") // Remove " ET"
        val formatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH)
        LocalTime.parse(cleanedTime, formatter)
    } catch (e: Exception) {
       null
    }
}
