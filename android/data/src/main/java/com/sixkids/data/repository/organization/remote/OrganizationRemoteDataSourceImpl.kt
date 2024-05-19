package com.sixkids.data.repository.organization.remote

import com.sixkids.data.api.MemberOrgService
import com.sixkids.data.api.OrganizationService
import com.sixkids.data.model.request.GreetingRequest
import com.sixkids.data.model.request.JoinOrganizationRequest
import com.sixkids.data.model.request.NewOrganizationRequest
import com.sixkids.data.model.response.ClassSummaryResponse
import com.sixkids.data.model.response.RankResponse
import com.sixkids.data.model.response.toModel
import com.sixkids.model.ClassSummary
import com.sixkids.model.MemberDetail
import com.sixkids.model.MemberSimple
import com.sixkids.model.MemberSimpleWithScore
import com.sixkids.model.Organization
import com.sixkids.model.StudentRelation
import javax.inject.Inject

class OrganizationRemoteDataSourceImpl @Inject constructor(
    private val organizationService: OrganizationService,
    private val memberOrgService: MemberOrgService
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

    override suspend fun getOrganizationSummary(organizationId: Int): ClassSummary {
        return organizationService.getOrganizationSummary(organizationId).getOrThrow().data.toModel()
    }

    override suspend fun updateOrganization(organizationId: Int, name: String): String {
        return organizationService.updateOrganization(organizationId, NewOrganizationRequest(name))
            .getOrThrow().data.name
    }

    override suspend fun getOrganizationInviteCode(organizationId: Int): String {
        return organizationService.getOrganizationInviteCode(organizationId).getOrThrow().data.code
    }
    
    override suspend fun getOrganizationMembers(orgId: Int): List<MemberSimple> {
        return organizationService.getOrganizationMembers(orgId).getOrThrow().data.map { it.toModel() }
    }

    override suspend fun getStudentDetail(orgId: Long, studentId: Long): MemberDetail {
        return memberOrgService.getMemberDetail(orgId, studentId).getOrThrow().data.toModel()
    }

    override suspend fun getStudentRelation(
        orgId: Long,
        studentId: Long,
        limit: Int?
    ): List<MemberSimpleWithScore> {
        return memberOrgService.getRelationSimple(orgId, studentId.toInt(), limit).getOrThrow().data.map { it.toModel() }
    }

    override suspend fun getStudentRelationDetail(
        orgId: Long,
        sourceStudentId: Long,
        targetStudentId: Long
    ): StudentRelation {
        return memberOrgService.getRelationDetail(orgId, sourceStudentId.toInt(), targetStudentId.toInt()).getOrThrow().data.toModel()
    }

    override suspend fun getOrganizationRank(orgId: Int): List<RankResponse> {
        return organizationService.getOrganizationRank(orgId).getOrThrow().data
    }
    
    override suspend fun tagGreeting(orgId: Long, memberId: Long): Int {
        return memberOrgService.tagGreeting(GreetingRequest(orgId, memberId)).getOrThrow().data
    }
}