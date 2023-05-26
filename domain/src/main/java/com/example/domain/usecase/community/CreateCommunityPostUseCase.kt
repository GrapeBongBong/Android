package com.example.domain.usecase.community

import com.example.domain.repository.CommunityRepository
import javax.inject.Inject

class CreateCommunityPostUseCase @Inject constructor(
    private val repository: CommunityRepository
) {
    suspend operator fun invoke(
        title: String,
        content: String
    ) = repository.createPost(title = title, content = content)
}