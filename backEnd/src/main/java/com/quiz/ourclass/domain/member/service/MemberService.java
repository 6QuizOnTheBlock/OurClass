package com.quiz.ourclass.domain.member.service;

import com.quiz.ourclass.domain.member.dto.TokenDTO;
import com.quiz.ourclass.domain.member.dto.request.MemberAdditionalInfoRequest;
import com.quiz.ourclass.domain.member.dto.request.MemberSignUpRequest;
import com.quiz.ourclass.domain.member.dto.request.MemberSigninRequest;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.entity.Role;
import com.quiz.ourclass.domain.member.entity.SocialType;
import com.quiz.ourclass.domain.member.repository.MemberRepository;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.AwsS3ObjectStorage;
import com.quiz.ourclass.global.util.UserDetailsImpl;
import com.quiz.ourclass.global.util.jwt.JwtUtil;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;
    private final AwsS3ObjectStorage awsS3ObjectStorage;

    public TokenDTO signUpProcess(MemberSignUpRequest request) {

        if (memberRepository.existsByEmail(request.getEmail())) {
            throw new GlobalException(ErrorCode.EXISTING_MEMBER);
        } else {
            Member member = memberRepository.save(
                Member.Guest(request.getEmail(), request.getName(), checkSocialType(
                    request.getSocialType())));

            String accessToken = jwtUtil.createToken(member, true);
            String refreshToken = jwtUtil.createToken(member, false);

            return TokenDTO.of(accessToken, refreshToken);
        }
    }

    public void addingInfoProcess(UserDetailsImpl userDetails,
        MemberAdditionalInfoRequest request) {
        Member member = userDetails.getMember();

        try {
            member.setProfileImage(awsS3ObjectStorage.uploadFile(request.getFile()));
        } catch (IOException e) {
            throw new GlobalException(ErrorCode.AWS_SERVER_ERROR);
        }
        member.setRole(request.getRole().equals("teacher") ? Role.TEACHER : Role.STUDENT);
        memberRepository.save(member);
    }

    public TokenDTO signInProcess(MemberSigninRequest request) {
        if (!memberRepository.existsByEmail(request.getEmail())) {
            throw new GlobalException(ErrorCode.NOT_FOUND_MEMBER);
        } else {
            Member member = memberRepository.findByEmail(request.getEmail()).orElseThrow();
            String accessToken = jwtUtil.createToken(member, true);
            String refreshToken = jwtUtil.createToken(member, false);

            return TokenDTO.of(accessToken, refreshToken);
        }
    }

    private SocialType checkSocialType(String socialType) {
        return switch (socialType) {
            case "kakao", "KAKAO" -> SocialType.KAKAO;
            case "google", "GOOGLE" -> SocialType.GOOGLE;
            case "naver", "NAVER" -> SocialType.NAVER;
            default -> null;
        };
    }
}
