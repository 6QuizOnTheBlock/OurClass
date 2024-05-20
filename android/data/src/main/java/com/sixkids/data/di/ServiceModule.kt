package com.sixkids.data.di

import com.sixkids.data.api.ChallengeService
import com.sixkids.data.api.ChatFilterService
import com.sixkids.data.api.CommentService
import com.sixkids.data.api.ChattingService
import com.sixkids.data.api.GroupService
import com.sixkids.data.api.MemberOrgService
import com.sixkids.data.api.MemberService
import com.sixkids.data.api.OrganizationService
import com.sixkids.data.api.PostService
import com.sixkids.data.api.RelayService
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

    @Singleton
    @Provides
    fun provideCommentService(
        @NetworkModule.AuthRetrofit retrofit: Retrofit
    ): CommentService {
        return retrofit.create(CommentService::class.java)
    }

    @Singleton
    @Provides
    fun provideChattingService(
        @NetworkModule.AuthRetrofit retrofit: Retrofit
    ): ChattingService {
        return retrofit.create(ChattingService::class.java)
    }

    @Singleton
    @Provides
    fun provideRelayService(
        @NetworkModule.AuthRetrofit retrofit: Retrofit
    ): RelayService {
        return retrofit.create(RelayService::class.java)
    }

    @Singleton
    @Provides
    fun provideMemberOrgService(
        @NetworkModule.AuthRetrofit retrofit: Retrofit
    ): MemberOrgService {
        return retrofit.create(MemberOrgService::class.java)
    }

    @Singleton
    @Provides
    fun provideChatFilterService(
        @NetworkModule.AuthRetrofit retrofit: Retrofit
    ): ChatFilterService {
        return retrofit.create(ChatFilterService::class.java)
    }
    
    @Singleton
    @Provides
    fun provideGroupService(
        @NetworkModule.AuthRetrofit retrofit: Retrofit
    ): GroupService {
        return retrofit.create(GroupService::class.java)
    }

}
