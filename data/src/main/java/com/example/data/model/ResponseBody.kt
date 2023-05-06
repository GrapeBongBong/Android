package com.example.data.model

data class ResponseBody<T>(
    val message: String,
    val status: Int,
    val data: T
)