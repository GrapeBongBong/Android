package com.example.domain.usecase.user

import com.example.domain.repository.UserRepository
import javax.inject.Inject

class GetCompletedMatchesUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke() = repository.myCompletedMatches()
}