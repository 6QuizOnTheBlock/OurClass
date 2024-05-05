package com.sixkids.data.repository.organization.remote

import com.sixkids.domain.repository.OrganizationRepository
import com.sixkids.model.Organization
import javax.inject.Inject

class OrganizationRepositoryImpl @Inject constructor(
    private val organizationRemoteDataSource: OrganizationRemoteDataSource
) : OrganizationRepository  {
    override suspend fun getClassList(): List<Organization> {
        return organizationRemoteDataSource.getClassList()
    }
}