package com.sixkids.data.repository.organization.remote

import com.sixkids.data.api.OrganizationService
import com.sixkids.data.model.response.toModel
import com.sixkids.model.Organization
import javax.inject.Inject

class OrganizationRemoteDataSourceImpl @Inject constructor(
    private val organizationService: OrganizationService
) : OrganizationRemoteDataSource{
    override suspend fun getClassList(): List<Organization> {
        return organizationService.getOrganizationList().getOrThrow().data.map { it.toModel() }
    }
}