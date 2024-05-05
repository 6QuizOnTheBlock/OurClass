package com.sixkids.data.di

import com.sixkids.data.repository.challenge.remote.ChallengeRemoteDataSource
import com.sixkids.data.repository.challenge.remote.ChallengeRemoteDataSourceImpl
import com.sixkids.data.repository.user.remote.UserDataSource
import com.sixkids.data.repository.user.remote.UserDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindUserDataSource(
        userDataSource: UserDataSourceImpl
    ): UserDataSource

    @Binds
    abstract fun bindChallengeDataSource(
        challengeRemoteDataSource: ChallengeRemoteDataSourceImpl
    ): ChallengeRemoteDataSource

}
