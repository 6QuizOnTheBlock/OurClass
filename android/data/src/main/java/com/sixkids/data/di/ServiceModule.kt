package com.sixkids.data.di

import com.sixkids.data.api.ChallengeService
import com.sixkids.data.api.MemberService
import com.sixkids.data.api.OrganizationService
import com.sixkids.data.api.PostService
import com.sixkids.data.api.SignInService
import com.sixkids.data.api.TokenService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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

    @Singleton
    @Provides
    fun provideSignInService(
        @NetworkModule.PublicRetrofit retrofit: Retrofit
    ): SignInService {
        return retrofit.create(SignInService::class.java)
    }

    @Singleton
    @Provides
    fun provideChallengeService(
        @NetworkModule.AuthRetrofit retrofit: Retrofit
    ): ChallengeService {
        return retrofit.create(ChallengeService::class.java)
    }

    @Singleton
    @Provides
    fun provideMemberService(
        @NetworkModule.AuthRetrofit retrofit: Retrofit
    ): MemberService {
        return retrofit.create(MemberService::class.java)
    }

    @Singleton
    @Provides
    fun provideOrganizationService(
        @NetworkModule.AuthRetrofit retrofit: Retrofit
    ): OrganizationService {
        return retrofit.create(OrganizationService::class.java)
    }

    @Singleton
    @Provides
    fun providePostService(
        @NetworkModule.AuthRetrofit retrofit: Retrofit
    ): PostService {
        return retrofit.create(PostService::class.java)
    }

}
