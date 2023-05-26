package com.example.domain.usecase.community

import com.example.domain.repository.CommunityRepository
import javax.inject.Inject

class UpdateCommunityPostUseCase @Inject constructor(
    private val repository: CommunityRepository
) {

}