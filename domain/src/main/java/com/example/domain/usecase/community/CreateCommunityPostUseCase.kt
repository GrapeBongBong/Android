package com.example.domain.usecase.community

import com.example.domain.repository.CommunityRepository
import java.io.File
import javax.inject.Inject

class CreateCommunityPostUseCase @Inject constructor(
    private val repository: CommunityRepository
) {
    suspend operator fun invoke(
        title: String,
        content: String,
        images: List<File?>
    ) = repository.createPost(title = title, content = content, images = images)
}