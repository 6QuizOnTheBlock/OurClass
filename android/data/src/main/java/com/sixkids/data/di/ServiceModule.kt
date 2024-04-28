package com.sixkids.data.di

import com.sixkids.data.api.TokenService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideTokenService(
        @NetworkModule.RefreshRetrofit retrofit: Retrofit
    ): TokenService {
        return retrofit.create(TokenService::class.java)
    }
}