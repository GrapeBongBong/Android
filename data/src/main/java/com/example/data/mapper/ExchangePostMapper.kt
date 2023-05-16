package com.example.data.mapper

import com.example.data.model.exchangePost.ExchangePostDto
import com.example.domain.model.exchange.ExchangePost

fun ExchangePostDto.toEntity() = ExchangePost(
    availableTime = availableTime,
    content = content,
    date = date,
    giveCate = giveCate,
    giveTalent = giveTalent,
    images = images,
    pid = pid,
    postType = postType,
    takeCate = takeCate,
    takeTalent = takeTalent,
    title = title,
    uid = uid,
    writerId = writerId,
    writerNick = writerNick
)