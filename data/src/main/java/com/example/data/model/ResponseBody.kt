package com.example.data.model

data class ResponseBody<T>(
    val message: String,
    val data: T
)