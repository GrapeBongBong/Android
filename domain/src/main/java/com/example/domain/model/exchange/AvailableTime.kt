package com.example.domain.model.exchange

data class AvailableTime(
    val days: List<String> = mutableListOf(),
    val timezone: String? = null
)