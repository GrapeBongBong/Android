package com.example.data.di

import com.example.data.source.AuthRemoteDataSource
import com.example.data.source.AuthRemoteDataSourceImpl
import com.example.data.source.ExchangePostRemoteDataSource
import com.example.data.source.ExchangePostRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RemoteModule {


    @Singleton
    @Binds
    abstract fun bindsAuthRemoteDataSource(
        authRemoteDataSourceImpl: AuthRemoteDataSourceImpl
    ): AuthRemoteDataSource

    @Singleton
    @Binds
    abstract fun bindExchangePostRemoteDataSource(
        exchangePostRemoteDataSourceImpl: ExchangePostRemoteDataSourceImpl
    ): ExchangePostRemoteDataSource

}