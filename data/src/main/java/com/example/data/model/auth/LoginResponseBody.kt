package com.example.data.model.auth

import com.example.data.model.user.UserDto

data class LoginResponseBody(
    val message: String,
    val token: String,
    val user: UserDto
)