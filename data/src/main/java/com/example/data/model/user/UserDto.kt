package com.example.data.model.user

import android.graphics.Bitmap

data class UserDto(
    val id: String,
    val password: String,
    val name: String,
    val nickName: String,
    val birth: String,
    val email: String,
    val phone_num: String,
    val address: String,
    val activated: Boolean,
    val gender: String,
    val temperature: Double,
    val profile_img: Bitmap?,
    val roles: List<String>,
    val uid: Int
)