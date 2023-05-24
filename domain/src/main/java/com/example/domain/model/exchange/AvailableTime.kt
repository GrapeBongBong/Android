package com.example.domain.model.exchange

data class AvailableTime(
    val days: MutableList<String> = mutableListOf(),
    val timezone: String? = null
)