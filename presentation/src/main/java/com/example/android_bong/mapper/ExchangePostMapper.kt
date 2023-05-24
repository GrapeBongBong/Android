package com.example.android_bong.mapper

import com.example.android_bong.view.main.talentexchange.TalentExchangeItemUiState
import com.example.domain.model.exchange.ExchangePost

fun ExchangePost.toUiState() = TalentExchangeItemUiState(
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
    writerNick = writerNick,
    status = status
)