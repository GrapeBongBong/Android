package com.example.domain.usecase.user

import com.example.domain.repository.AuthRepository
import com.example.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(userId: Int, nickName: String, address: String): Result<String> {
        val result =
            userRepository.updateUserInfo(nickName = nickName, address = address, userId = userId)
        if (result.isSuccess) {
            authRepository.syncCurrentUser(
                nickName = nickName,
                address = address
            )
        }
        return result
    }
}