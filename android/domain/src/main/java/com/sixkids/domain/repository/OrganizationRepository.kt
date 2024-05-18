package com.sixkids.domain.repository

import com.sixkids.model.MemberDetail
import com.sixkids.model.ClassSummary
import com.sixkids.model.MemberSimple
import com.sixkids.model.MemberSimpleWithScore
import com.sixkids.model.Organization

interface OrganizationRepository {
    suspend fun getClassList(): List<Organization>

    suspend fun saveSelectedOrganizationId(organizationId: Int)
    suspend fun getSelectedOrganizationId(): Int

    suspend fun newOrganization(name: String): Long

    suspend fun joinOrganization(orgId: Int, code: String): Long

    suspend fun getOrganizationSummary(organizationId: Int): ClassSummary

    suspend fun updateOrganization(organizationId: Int, name: String): String

    suspend fun getOrganizationInviteCode(organizationId: Int): String

    suspend fun saveSelectedOrganizationName(organizationName: String)

    suspend fun loadSelectedOrganizationName(): String

    suspend fun getOrganizationMembers(orgId: Int): List<MemberSimple>

    suspend fun getStudentDetail(orgId: Long, studentId: Long): MemberDetail

    suspend fun getStudentRelation(orgId: Long, studentId: Long, limit: Int?): List<MemberSimpleWithScore>
}