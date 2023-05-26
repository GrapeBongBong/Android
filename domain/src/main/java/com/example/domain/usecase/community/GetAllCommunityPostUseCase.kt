package com.example.domain.usecase.community

import com.example.domain.repository.CommunityRepository
import javax.inject.Inject

class GetAllCommunityPostUseCase @Inject constructor(
    private val repository: CommunityRepository
) {
    suspend operator fun invoke() = repository.getAllPost()
}