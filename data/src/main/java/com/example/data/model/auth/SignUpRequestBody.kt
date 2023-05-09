package com.example.data.model.auth

data class SignUpRequestBody(
    val id: String,
    val password: String,
    val name: String,
    val nickName: String,
    val birth: String,
    val email: String,
    val phoneNum: String,
    val address: String,
    val gender: String
)