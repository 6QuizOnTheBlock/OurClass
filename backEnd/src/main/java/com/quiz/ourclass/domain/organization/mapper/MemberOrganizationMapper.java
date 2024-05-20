package com.quiz.ourclass.domain.organization.mapper;

import com.quiz.ourclass.domain.organization.dto.response.OrganizationHomeResponse;
import com.quiz.ourclass.domain.organization.dto.response.RelationSimpleResponse;
import com.quiz.ourclass.domain.organization.entity.MemberOrganization;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MemberOrganizationMapper {

    @Mapping(target = "name", source = "memberOrg.member.name")
    @Mapping(target = "photo", source = "memberOrg.member.profileImage")
    @Mapping(target = "exp", source = "memberOrg.exp")
    @Mapping(target = "organizationName", source = "memberOrg.organization.name")
    @Mapping(target = "relations", source = "friendlyResponse")
    OrganizationHomeResponse memberOrgToOrganizationHome(MemberOrganization memberOrg,
        List<RelationSimpleResponse> friendlyResponse);
}
