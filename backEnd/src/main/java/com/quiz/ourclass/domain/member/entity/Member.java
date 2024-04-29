package com.quiz.ourclass.domain.member.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String email;
    String name;
    String profileImage;
    @Enumerated(EnumType.STRING)
    SocialType socialType;
    @Enumerated(EnumType.STRING)
    Role role;

    @Builder
    private Member (String email, String name, String profileImage, SocialType socialType, Role role){
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
        this.socialType = socialType;
        this.role = role;
    }


    public static Member Guest (String email, String name, SocialType socialType) {
        return Member.builder()
            .email(email)
            .name(name)
            .socialType(socialType)
            .role(Role.GUEST)
            .build();
    }

    public static Member addInfo (Member member, String profileImage, String role){
        member.setProfileImage(profileImage);
        member.setRole(role.equals("TEACHER")? Role.TEACHER : Role.STUDENT);

        return  member;
    }
}
