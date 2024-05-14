package com.sixkids.data.repository.organization.remote

import com.sixkids.data.api.OrganizationService
import com.sixkids.data.model.request.JoinOrganizationRequest
import com.sixkids.data.model.request.NewOrganizationRequest
import com.sixkids.data.model.response.ClassSummaryResponse
import com.sixkids.data.model.response.toModel
import com.sixkids.model.Organization
import javax.inject.Inject

class OrganizationRemoteDataSourceImpl @Inject constructor(
    private val organizationService: OrganizationService
) : OrganizationRemoteDataSource {
    override suspend fun getClassList(): List<Organization> {
        return organizationService.getOrganizationList().getOrThrow().data.map { it.toModel() }
    }

    override suspend fun newOrganization(name: String): Long {
        return organizationService.newOrganization(NewOrganizationRequest(name)).getOrThrow().data
    }

    override suspend fun joinOrganization(orgId: Int, code: String): Long {
        return organizationService.joinOrganization(orgId, JoinOrganizationRequest(code))
            .getOrThrow().data
    }

    override suspend fun getOrganizationSummary(organizationId: Int): ClassSummaryResponse {
        return organizationService.getOrganizationSummary(organizationId).getOrThrow().data
    }

    override suspend fun updateOrganization(organizationId: Int, name: String): String {
        return organizationService.updateOrganization(organizationId, NewOrganizationRequest(name))
            .getOrThrow().data.name
    }
}