package com.example.domain.usecase.user

import com.example.domain.repository.AuthRepository
import com.example.domain.repository.UserRepository
import java.io.File
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(
        userId: Int,
        nickName: String,
        email: String,
        phoneNumber: String,
        password: String,
        profileImage: File?
    ): Result<String> {
        val result =
            userRepository.updateUserInfo(
                nickName = nickName,
                email = email,
                phoneNumber = phoneNumber,
                password = password,
                userId = userId,
                profileImage = profileImage
            )
        if (result.isSuccess) {
            authRepository.syncCurrentUser(
                nickName = nickName,
                email = email,
                phoneNumber = phoneNumber,
                password = password,
                profileImage = profileImage
            )
        }
        return result
    }
}