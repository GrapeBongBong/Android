package com.example.domain.usecase.user

import com.example.domain.repository.UserRepository
import javax.inject.Inject

class GetMyCommunityPostUseCase @Inject constructor(
    private val repository: UserRepository
) {
    suspend operator fun invoke() = repository.myCommunityPost()
}