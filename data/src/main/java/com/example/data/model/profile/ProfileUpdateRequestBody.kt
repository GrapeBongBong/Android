package com.example.data.model.profile

data class ProfileUpdateRequestBody(
    val nickName: String,
    val email: String,
    val phoneNumber: String,
    val password: String
)