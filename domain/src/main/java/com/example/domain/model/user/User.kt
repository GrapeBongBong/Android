package com.example.domain.model.user

import java.io.Serializable

data class User(
    val activated: Boolean,
    val address: String,
    val birth: String,
    val email: String,
    val gender: String,
    val id: String,
    val name: String,
    val nickName: String,
    val password: String,
    val phone_num: String,
    val profile_img: Any?,
    val roles: List<Any>,
    val temperature: Double,
    val uid: Int
) : Serializable