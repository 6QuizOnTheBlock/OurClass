package com.sixkids.data.di

import com.sixkids.data.repository.organization.remote.OrganizationRemoteDataSource
import com.sixkids.data.repository.organization.remote.OrganizationRemoteDataSourceImpl
import com.sixkids.data.repository.user.local.UserLocalDataSource
import com.sixkids.data.repository.user.local.UserLocalDataSourceImpl
import com.sixkids.data.repository.user.remote.UserRemoteDataSource
import com.sixkids.data.repository.user.remote.UserRemoteDataSourceImpl
import com.sixkids.data.repository.challenge.remote.ChallengeRemoteDataSource
import com.sixkids.data.repository.challenge.remote.ChallengeRemoteDataSourceImpl
import com.sixkids.data.repository.comment.remote.CommentRemoteDataSource
import com.sixkids.data.repository.comment.remote.CommentRemoteDataSourceImpl
import com.sixkids.data.repository.chatting.remote.ChattingRemoteDataSource
import com.sixkids.data.repository.chatting.remote.ChattingRemoteDataSourceImpl
import com.sixkids.data.repository.organization.local.OrganizationLocalDataSource
import com.sixkids.data.repository.organization.local.OrganizationLocalDataSourceImpl
import com.sixkids.data.repository.post.remote.PostRemoteDataSource
import com.sixkids.data.repository.post.remote.PostRemoteDataSourceImpl
import com.sixkids.data.repository.relay.remote.RelayRemoteDataSource
import com.sixkids.data.repository.relay.remote.RelayRemoteDataSourceImpl
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

    @Binds
    abstract fun bindPostRemoteDataSource(
        postRemoteDataSource: PostRemoteDataSourceImpl
    ): PostRemoteDataSource

    @Binds
    abstract fun bindCommentRemoteDataSource(
        commentRemoteDataSource: CommentRemoteDataSourceImpl
    ): CommentRemoteDataSource
  
    @Binds
    abstract fun bindChattingRemoteDataSource(
        chattingRemoteDataSource: ChattingRemoteDataSourceImpl
    ): ChattingRemoteDataSource

    @Binds
    abstract fun bindRelayRemoteDataSource(
        relayRemoteDataSource: RelayRemoteDataSourceImpl
    ): RelayRemoteDataSource
}
