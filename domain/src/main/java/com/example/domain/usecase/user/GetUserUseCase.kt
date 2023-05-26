package com.example.domain.usecase.user

import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    operator fun invoke() = authRepository.getUserDetail().value
}