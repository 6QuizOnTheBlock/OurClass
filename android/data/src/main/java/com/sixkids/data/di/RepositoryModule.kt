package com.sixkids.data.di

import com.sixkids.data.repository.TokenRepositoryImpl
import com.sixkids.data.repository.challenge.ChallengeRepositoryImpl
import com.sixkids.domain.repository.ChallengeRepository
import com.sixkids.domain.repository.TokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindTokenRepository(
        tokenRepository: TokenRepositoryImpl
    ): TokenRepository

    @Binds
    abstract fun bindChallengeRepository(
        challengeRepository: ChallengeRepositoryImpl
    ): ChallengeRepository
}
