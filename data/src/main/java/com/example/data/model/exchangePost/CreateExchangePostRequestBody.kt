package com.example.data.model.exchangePost

import com.example.domain.model.exchange.AvailableTime

data class CreateExchangePostRequestBody(
    val title: String,
    val content: String,
    val giveCate: String,
    val takeCate: String,
    val giveTalent: String,
    val takeTalent: String,
    val availableTime: AvailableTime
)