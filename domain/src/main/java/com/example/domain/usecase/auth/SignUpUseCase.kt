package com.example.domain.usecase.auth

import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        id: String,
        password: String,
        name: String,
        nickName: String,
        birth: String,
        email: String,
        phoneNum: String,
        address: String,
        gender: String
    ) =
        authRepository.signUp(
            id = id,
            password = password,
            name = name,
            nickName = nickName,
            birth = birth,
            email = email,
            phoneNum = phoneNum,
            address = address,
            gender = gender
        )
}