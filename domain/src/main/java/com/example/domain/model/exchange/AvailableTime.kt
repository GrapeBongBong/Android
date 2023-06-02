package com.example.domain.model.exchange

data class AvailableTime(
    val days: List<String>? = emptyList(),
    val timezone: String? = null
)