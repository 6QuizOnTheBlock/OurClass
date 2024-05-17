package com.sixkids.domain.repository

import com.sixkids.model.MemberSimple
import com.sixkids.model.Organization

interface OrganizationRepository {
    suspend fun getClassList(): List<Organization>

    suspend fun saveSelectedOrganizationId(organizationId: Int)
    suspend fun getSelectedOrganizationId(): Int

    suspend fun newOrganization(name: String): Long

    suspend fun joinOrganization(orgId: Int, code: String): Long

    suspend fun getOrganizationMembers(orgId: Int): List<MemberSimple>
}