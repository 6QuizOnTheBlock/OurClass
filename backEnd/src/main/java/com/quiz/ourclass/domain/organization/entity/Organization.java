package com.quiz.ourclass.domain.organization.entity;

import com.quiz.ourclass.domain.board.entity.Post;
import com.quiz.ourclass.domain.member.entity.Member;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String name;
    @ManyToOne(fetch = FetchType.LAZY)
    Member manager;
    @OneToMany(mappedBy = "organization")
    private List<MemberOrganization> memberOrganizations;
    @OneToMany(mappedBy = "organization")
    private List<Post> posts;
    int memberCount;
    LocalDate createTime;
}
