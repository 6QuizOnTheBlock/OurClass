package com.sixkids.data.di

import com.sixkids.data.repository.organization.remote.OrganizationRemoteDataSource
import com.sixkids.data.repository.organization.remote.OrganizationRemoteDataSourceImpl
import com.sixkids.data.repository.user.local.UserLocalDataSource
import com.sixkids.data.repository.user.local.UserLocalDataSourceImpl
import com.sixkids.data.repository.user.remote.UserRemoteDataSource
import com.sixkids.data.repository.user.remote.UserRemoteDataSourceImpl
import com.sixkids.data.repository.challenge.remote.ChallengeRemoteDataSource
import com.sixkids.data.repository.challenge.remote.ChallengeRemoteDataSourceImpl
import com.sixkids.data.repository.organization.local.OrganizationLocalDataSource
import com.sixkids.data.repository.organization.local.OrganizationLocalDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    abstract fun bindUserDataSource(
        userDataSource: UserRemoteDataSourceImpl
    ): UserRemoteDataSource

    @Binds
    abstract fun bindLocalUserDataSource(
        userLocalDataSource: UserLocalDataSourceImpl
    ): UserLocalDataSource

    @Binds
    abstract fun bindChallengeDataSource(
        challengeRemoteDataSource: ChallengeRemoteDataSourceImpl
    ): ChallengeRemoteDataSource
  
    @Binds
    abstract fun bindOrganizationRemoteDataSource(
        organizationRemoteDataSource: OrganizationRemoteDataSourceImpl
    ): OrganizationRemoteDataSource

    @Binds
    abstract fun bindLocalOrganizationDataSource(
        organizationLocalDataSource: OrganizationLocalDataSourceImpl
    ): OrganizationLocalDataSource

}
