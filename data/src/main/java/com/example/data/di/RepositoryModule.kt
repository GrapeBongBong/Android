package com.example.data.di

import com.example.data.repository.*
import com.example.domain.repository.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindsAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ): AuthRepository

    @Singleton
    @Binds
    abstract fun bindsExchangePostRepository(
        exchangePostRepositoryImpl: ExchangePostRepositoryImpl
    ): ExchangePostRepository

    @Singleton
    @Binds
    abstract fun bindsUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Singleton
    @Binds
    abstract fun bindsCommentRepository(
        commentRepositoryImpl: CommentRepositoryImpl
    ): CommentRepository

    @Singleton
    @Binds
    abstract fun bindsCommunity(
        communityRepositoryImpl: CommunityRepositoryImpl
    ): CommunityRepository

}