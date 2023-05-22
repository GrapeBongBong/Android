package com.example.domain.usecase.user

import com.example.domain.repository.UserRepository
import javax.inject.Inject

class UpdateUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository
) {
    suspend operator fun invoke( userId: Int, nickName: String, address: String) =
        userRepository.updateUserInfo(nickName = nickName, address = address, userId = userId)
}