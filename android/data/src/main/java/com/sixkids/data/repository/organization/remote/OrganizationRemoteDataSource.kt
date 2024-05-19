package com.sixkids.data.repository.organization.remote

import com.sixkids.model.ClassSummary
import com.sixkids.model.MemberDetail
import com.sixkids.model.MemberSimple
import com.sixkids.model.MemberSimpleWithScore
import com.sixkids.model.Organization
import com.sixkids.model.StudentRelation

interface OrganizationRemoteDataSource {
    suspend fun getClassList(): List<Organization>

    suspend fun newOrganization(name: String): Long

    suspend fun joinOrganization(orgId: Int, code: String): Long

    suspend fun getOrganizationSummary(organizationId: Int): ClassSummary

    suspend fun updateOrganization(organizationId: Int, name: String): String

    suspend fun getOrganizationInviteCode(organizationId: Int): String

    suspend fun getOrganizationMembers(orgId: Int): List<MemberSimple>

    suspend fun getStudentDetail(orgId: Long, studentId: Long): MemberDetail

    suspend fun getStudentRelation(orgId: Long, studentId: Long, limit: Int?): List<MemberSimpleWithScore>

    suspend fun getStudentRelationDetail(orgId: Long, sourceStudentId: Long, targetStudentId: Long): StudentRelation

    suspend fun tagGreeting(orgId: Long, memberId: Long): Int
}