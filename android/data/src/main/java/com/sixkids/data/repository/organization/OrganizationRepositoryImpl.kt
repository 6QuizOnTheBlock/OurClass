package com.sixkids.data.repository.organization

import com.sixkids.data.model.response.toModel
import com.sixkids.data.repository.organization.local.OrganizationLocalDataSource
import com.sixkids.data.repository.organization.remote.OrganizationRemoteDataSource
import com.sixkids.domain.repository.OrganizationRepository
import com.sixkids.model.MemberDetail
import com.sixkids.model.ClassSummary
import com.sixkids.model.MemberSimple
import com.sixkids.model.MemberSimpleWithScore
import com.sixkids.model.Organization
import com.sixkids.model.StudentRelation
import javax.inject.Inject

class OrganizationRepositoryImpl @Inject constructor(
    private val organizationRemoteDataSource: OrganizationRemoteDataSource,
    private val organizationLocalDataSource: OrganizationLocalDataSource
) : OrganizationRepository  {
    override suspend fun getClassList(): List<Organization> {
        return organizationRemoteDataSource.getClassList()
    }

    override suspend fun saveSelectedOrganizationId(organizationId: Int) {
        organizationLocalDataSource.saveSelectedOrganizationId(organizationId)
    }

    override suspend fun getSelectedOrganizationId(): Int {
        return organizationLocalDataSource.getSelectedOrganizationId()
    }

    override suspend fun newOrganization(name: String): Long {
        return organizationRemoteDataSource.newOrganization(name)
    }

    override suspend fun joinOrganization(orgId: Int, code: String): Long {
        return organizationRemoteDataSource.joinOrganization(orgId, code)
    }

    override suspend fun getOrganizationSummary(organizationId: Int): ClassSummary {
        organizationRemoteDataSource.getOrganizationSummary(organizationId).let {
            return ClassSummary(
                it.challengeCounts.map { challengeCount -> challengeCount.toModel() },
                it.relayCounts.map { challengeCount -> challengeCount.toModel() },
                it.postsCounts.map { challengeCount -> challengeCount.toModel() },
            )
        }
    }

    override suspend fun updateOrganization(organizationId: Int, name: String): String {
        return organizationRemoteDataSource.updateOrganization(organizationId, name)
    }

    override suspend fun getOrganizationInviteCode(organizationId: Int): String {
        return organizationRemoteDataSource.getOrganizationInviteCode(organizationId)
    }

    override suspend fun saveSelectedOrganizationName(organizationName: String) {
        organizationLocalDataSource.saveSelectedOrganizationName(organizationName)
    }

    override suspend fun loadSelectedOrganizationName(): String {
        return organizationLocalDataSource.getSelectedOrganizationName()
    }

    override suspend fun getOrganizationMembers(orgId: Int): List<MemberSimple> {
        return organizationRemoteDataSource.getOrganizationMembers(orgId)
    }

    override suspend fun getStudentDetail(orgId: Long, studentId: Long): MemberDetail {
        return organizationRemoteDataSource.getStudentDetail(orgId, studentId)
    }

    override suspend fun getStudentRelation(
        orgId: Long,
        studentId: Long,
        limit: Int?
    ): List<MemberSimpleWithScore> {
        return organizationRemoteDataSource.getStudentRelation(orgId, studentId, limit)
    }

    override suspend fun getStudentRelationDetail(
        orgId: Long,
        sourceStudentId: Long,
        targetStudentId: Long
    ): StudentRelation {
        return organizationRemoteDataSource.getStudentRelationDetail(orgId, sourceStudentId, targetStudentId)
    }
}