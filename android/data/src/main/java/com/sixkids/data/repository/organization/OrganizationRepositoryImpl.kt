package com.sixkids.data.repository.organization

import com.sixkids.data.repository.organization.local.OrganizationLocalDataSource
import com.sixkids.data.repository.organization.remote.OrganizationRemoteDataSource
import com.sixkids.domain.repository.OrganizationRepository
import com.sixkids.model.Organization
import javax.inject.Inject

class OrganizationRepositoryImpl @Inject constructor(
    private val organizationRemoteDataSource: OrganizationRemoteDataSource,
    private val organizationLocalDataSource: OrganizationLocalDataSource
) : OrganizationRepository  {
    override suspend fun getClassList(): List<Organization> {
        return organizationRemoteDataSource.getClassList()
    }

    override suspend fun saveSelectedOrganizationId(organizationId: Int) {
        organizationLocalDataSource.saveSelectedOrganizationId(organizationId)
    }

    override suspend fun getSelectedOrganizationId(): Int {
        return organizationLocalDataSource.getSelectedOrganizationId()
    }

    override suspend fun newOrganization(name: String): Long {
        return organizationRemoteDataSource.newOrganization(name)
    }
}