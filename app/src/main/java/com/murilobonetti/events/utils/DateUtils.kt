package com.murilobonetti.events.utils

import java.text.SimpleDateFormat
import java.util.*

fun formatTimestampToDateString(timestamp: Long): String {
    val formatter = SimpleDateFormat("dd/MM/yyyy - HH:mm", Locale.getDefault())
    return formatter.format(timestamp)
}