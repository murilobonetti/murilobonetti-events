package com.murilobonetti.events.data

import android.os.Parcelable
import com.murilobonetti.events.utils.formatTimestampToDateString
import kotlinx.parcelize.Parcelize

@Parcelize
data class Event(
    val id: String,
    val title: String,
    val price: Double,
    val latitude: Double,
    val longitude: Double,
    val image: String,
    val description: String,
    val date: Long
) : Parcelable {
    val formattedDate: String
        get() = formatTimestampToDateString(date)
}
