package com.example.domain.repository

import com.example.domain.model.community.CommunityPost

interface CommunityRepository {
    suspend fun getAllPost(): Result<List<CommunityPost>>

    suspend fun deletePost(postId: Int): Result<String>

    suspend fun createPost(
        title: String,
        content: String
    ): Result<String>

    suspend fun updatePost(
        postId: Int,
        title: String,
        content: String
    ): Result<String>

}