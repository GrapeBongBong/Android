package com.example.data.di

import com.example.data.BuildConfig
import com.example.data.api.*
import com.example.data.source.AuthLocalDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    companion object {
        private const val BASE_URL = "http://3.34.75.23:8080/"
        private const val TOKEN_HEADER_KEY = "Authorization"
    }

    @Provides
    @Singleton
    fun provideHttpClient(authLocalDataSource: AuthLocalDataSource): OkHttpClient {
        val client = OkHttpClient.Builder()
            .readTimeout(100, TimeUnit.SECONDS)
            .connectTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
            .addInterceptor(
                HttpLoggingInterceptor().apply {
                    this.level = if (BuildConfig.DEBUG) {
                        HttpLoggingInterceptor.Level.BODY
                    } else {
                        HttpLoggingInterceptor.Level.NONE
                    }
                }
            )
            .addInterceptor {
                val authLocalData = authLocalDataSource.getData()
                var newRequest = it.request()
                if (authLocalData != null) {
                    val token = "Bearer ${authLocalData.sessionToken}"
                    newRequest = newRequest.newBuilder().addHeader(
                        name = TOKEN_HEADER_KEY,
                        value = token
                    ).build()
                }
                it.proceed(newRequest)
            }

        return client.build()
    }


    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApiService(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideExchangePostApi(retrofit: Retrofit): ExchangePostApi {
        return retrofit.create(ExchangePostApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCommentApi(retrofit: Retrofit): CommentApi {
        return retrofit.create(CommentApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCommunityApi(retrofit: Retrofit): CommunityApi {
        return retrofit.create(CommunityApi::class.java)
    }
    
}