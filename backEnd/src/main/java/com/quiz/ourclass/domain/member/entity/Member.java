package com.quiz.ourclass.domain.member.entity;

import com.quiz.ourclass.domain.organization.entity.MemberOrganization;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SQLDelete(sql = "UPDATE member set deleted_at = CONVERT_TZ(NOW(), 'UTC','Asia/Seoul') WHERE id = ?")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @OneToMany(mappedBy = "member")
    List<MemberOrganization> memberOrganizations;
    String email;
    String name;
    String profileImage;
    @Enumerated(EnumType.STRING)
    SocialType socialType;
    @Enumerated(EnumType.STRING)
    Role role;
    @Column(nullable = true)
    private LocalDateTime deletedAt;

    @Builder
    private Member(String email, String name, String profileImage, SocialType socialType,
        Role role) {
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
        this.socialType = socialType;
        this.role = role;
    }


    public static Member of(String email, String name, SocialType socialType, String profileImage,
        Role role) {
        return Member.builder()
            .email(email)
            .name(name)
            .socialType(socialType)
            .role(Role.GUEST)
            .profileImage(profileImage)
            .role(role)
            .build();
    }

    public static Member addInfo(Member member, String profileImage, Role role) {
        member.setProfileImage(profileImage);
        member.setRole(role);

        return member;
    }
}
