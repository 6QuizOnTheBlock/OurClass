package com.sixkids.data.di

import com.sixkids.data.repository.TokenRepositoryImpl
import com.sixkids.data.repository.challenge.ChallengeRepositoryImpl
import com.sixkids.data.repository.comment.CommentRepositoryImpl
import com.sixkids.data.repository.chatting.ChattingRepositoryImpl
import com.sixkids.data.repository.organization.OrganizationRepositoryImpl
import com.sixkids.data.repository.post.PostRepositoryImpl
import com.sixkids.data.repository.relay.RelayRepositoryImpl
import com.sixkids.domain.repository.ChallengeRepository
import com.sixkids.data.repository.user.UserRepositoryImpl
import com.sixkids.domain.repository.CommentRepository
import com.sixkids.domain.repository.ChattingRepository
import com.sixkids.domain.repository.OrganizationRepository
import com.sixkids.domain.repository.PostRepository
import com.sixkids.domain.repository.RelayRepository
import com.sixkids.domain.repository.TokenRepository
import com.sixkids.domain.repository.UserRepository
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
    abstract fun bindTokenRepository(
        tokenRepository: TokenRepositoryImpl
    ): TokenRepository

    @Singleton
    @Binds
    abstract fun bindUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository

    @Singleton
    @Binds
    abstract fun bindChallengeRepository(
        challengeRepository: ChallengeRepositoryImpl
    ): ChallengeRepository

    @Singleton
    @Binds
    abstract fun bindOrganizationRepository(
        organizationRepository: OrganizationRepositoryImpl
    ): OrganizationRepository

    @Singleton
    @Binds
    abstract fun bindPostRepository(
        postRepository: PostRepositoryImpl
    ): PostRepository

    @Singleton
    @Binds
    abstract fun bindCommentRepository(
        commentRepository: CommentRepositoryImpl
    ): CommentRepository
  
    @Singleton
    @Binds
    abstract fun bindChattingRepository(
        chattingRepository: ChattingRepositoryImpl
    ): ChattingRepository

    @Singleton
    @Binds
    abstract fun bindRelayRepository(
        relayRepository: RelayRepositoryImpl
    ): RelayRepository
}
