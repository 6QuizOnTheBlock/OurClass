package com.sixkids.domain.repository

import com.sixkids.model.Organization

interface OrganizationRepository {
    suspend fun getClassList(): List<Organization>

    suspend fun saveSelectedOrganizationId(organizationId: Int)
    suspend fun getSelectedOrganizationId(): Int

    suspend fun newOrganization(name: String): Long
}