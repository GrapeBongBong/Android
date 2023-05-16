package com.example.data.mapper

import com.example.data.model.user.UserDto
import com.example.domain.model.user.User

fun UserDto.toEntity() = User(
    id = id,
    password = password,
    name = name,
    nickName = nickName,
    birth = birth,
    email = email,
    phone_num = phone_num,
    address = address,
    activated = activated,
    gender = gender,
    temperature = temperature,
    profile_img = profile_img,
    roles = roles,
    uid = uid
)