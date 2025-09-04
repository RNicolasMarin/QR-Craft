package com.qrcraft.core.presentation

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatTimestamp(millis: Long): String {
    val sdf = SimpleDateFormat("dd MMM yyyy, HH:mm", Locale.ENGLISH)
    return sdf.format(Date(millis))
}