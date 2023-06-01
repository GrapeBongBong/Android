package com.example.data.di

import com.example.data.repository.ChattingRepositoryImpl
import com.example.data.source.WebSocketDataSource
import com.example.data.source.WebSocketDataSourceImpl
import com.example.domain.repository.ChattingRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object AppModule {

    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .pingInterval(30, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    fun provideWebSocketDataSource(client: OkHttpClient): WebSocketDataSource {
        return WebSocketDataSourceImpl(client)
    }

    @Provides
    fun provideVideoRepository(webSocketDataSource: WebSocketDataSource): ChattingRepository {
        return ChattingRepositoryImpl(webSocketDataSource)
    }


}