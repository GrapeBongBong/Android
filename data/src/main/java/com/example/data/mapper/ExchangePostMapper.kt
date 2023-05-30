package com.example.data.mapper

import com.example.data.model.exchangePost.AvailableTimeDto
import com.example.data.model.exchangePost.ExchangePostDto
import com.example.data.model.exchangePost.ImageDto
import com.example.domain.model.exchange.AvailableTime
import com.example.domain.model.exchange.ExchangePost
import com.example.domain.model.exchange.Image

fun ExchangePostDto.toEntity() = ExchangePost(
    availableTime = availableTime.toEntity(),
    content = content,
    date = date,
    giveCate = giveCate,
    giveTalent = giveTalent,
    images = images?.map { it.toEntity() },
    pid = pid,
    postType = postType,
    takeCate = takeCate,
    takeTalent = takeTalent,
    title = title,
    uid = uid,
    writerId = writerId,
    writerNick = writerNick,
    status = status,
    writerImageURL = writerImageURL,
    likeCount = likeCount,
    liked = liked
)

fun AvailableTimeDto.toEntity() = AvailableTime(
    days = days as MutableList<String>,
    timezone = timezone
)

fun ImageDto.toEntity() = Image(
    fileUrl = fileUrl,
    id = id
)
