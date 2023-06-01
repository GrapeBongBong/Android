package com.example.domain.repository

import com.example.domain.model.community.CommunityPost
import java.io.File

interface CommunityRepository {
    suspend fun getAllPost(): Result<List<CommunityPost>>

    suspend fun deletePost(postId: Int): Result<String>

    suspend fun createPost(
        title: String,
        content: String,
        images: List<File?>
    ): Result<String>

    suspend fun updatePost(
        postId: Int,
        title: String,
        content: String
    ): Result<String>

    suspend fun clickLike(
        postId: Int
    ): Result<String>

    suspend fun clickUnLike(
        postId: Int
    ): Result<String>

    suspend fun getPopularCommunityPost(): Result<List<CommunityPost>>

}