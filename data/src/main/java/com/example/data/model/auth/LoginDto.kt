package com.example.data.model.auth

import com.example.data.model.user.UserDto

data class LoginDto(
    val token: String,
    val user: UserDto
)