package com.sixkids.domain.repository

import com.sixkids.model.Organization

interface OrganizationRepository {
    suspend fun getClassList(): List<Organization>
}