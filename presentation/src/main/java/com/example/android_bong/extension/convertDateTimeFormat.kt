package com.example.android_bong.extension

import java.text.SimpleDateFormat
import java.util.*

fun convertDateTimeFormat(dateTime: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    val date: Date = inputFormat.parse(dateTime) as Date
    return outputFormat.format(date)
}