package com.sixkids.data.repository.organization.remote

import com.sixkids.model.MemberSimple
import com.sixkids.model.Organization

interface OrganizationRemoteDataSource {
    suspend fun getClassList(): List<Organization>

    suspend fun newOrganization(name: String): Long

    suspend fun joinOrganization(orgId: Int, code: String): Long

    suspend fun getOrganizationMembers(orgId: Int): List<MemberSimple>
}