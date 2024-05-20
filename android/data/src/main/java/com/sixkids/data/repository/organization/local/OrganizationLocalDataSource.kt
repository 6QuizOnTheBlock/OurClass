package com.sixkids.data.repository.organization.local

interface OrganizationLocalDataSource {
    suspend fun getSelectedOrganizationId(): Int
    suspend fun saveSelectedOrganizationId(organizationId: Int)
    suspend fun getSelectedOrganizationName(): String
    suspend fun saveSelectedOrganizationName(organizationName: String)
}