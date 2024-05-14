package com.sixkids.data.repository.organization.remote

import com.sixkids.data.model.response.ApiResponse
import com.sixkids.data.model.response.ClassSummaryResponse
import com.sixkids.data.network.ApiResult
import com.sixkids.model.Organization
import retrofit2.http.Path

interface OrganizationRemoteDataSource {
    suspend fun getClassList(): List<Organization>

    suspend fun newOrganization(name: String): Long

    suspend fun joinOrganization(orgId: Int, code: String): Long

    suspend fun getOrganizationSummary(organizationId: Int): ClassSummaryResponse

    suspend fun updateOrganization(organizationId: Int, name: String): String

    suspend fun getOrganizationInviteCode(organizationId: Int): String
}