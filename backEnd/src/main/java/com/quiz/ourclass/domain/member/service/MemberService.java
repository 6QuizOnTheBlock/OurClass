package com.quiz.ourclass.domain.member.service;

import com.quiz.ourclass.domain.member.dto.OIDCDecodePayload;
import com.quiz.ourclass.domain.member.dto.TokenDTO;
import com.quiz.ourclass.domain.member.dto.request.MemberSignInRequest;
import com.quiz.ourclass.domain.member.dto.request.MemberSignUpRequest;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.entity.Role;
import com.quiz.ourclass.domain.member.entity.SocialType;
import com.quiz.ourclass.domain.member.repository.MemberRepository;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.AwsS3ObjectStorage;
import com.quiz.ourclass.global.util.jwt.JwtUtil;
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
    private final OidcService oidcService;


    public TokenDTO signUpProcess(MemberSignUpRequest request) {

        try {
          OIDCDecodePayload payload = oidcService.certificatingIdToken(request.getIdToken());

          String imgUrl = awsS3ObjectStorage.uploadFile(request.getFile());

          // 이미 존재하면,
          if(memberRepository.existsByEmail(payload.getEmail())){
              Member member = memberRepository.findByEmail(payload.getEmail()).orElse(null);
              // 존재하는데, 프로필 사진이나, 역할이 비었으면
              if(member.getProfileImage() == null || member.getRole() ==null || member.getRole().equals(
                  Role.GUEST)){
                Member.addInfo(member,imgUrl,request.getRole());
              }
              else {
                  throw new GlobalException(ErrorCode.EXISTING_MEMBER);
              }
          } else {
              memberRepository.save(Member.of(payload.getEmail(), payload.getNickname(), SocialType.KAKAO,imgUrl,
                  request.getRole()));
          }


        } catch (Exception e) {
            if (e instanceof GlobalException && ((GlobalException) e).getErrorCode() == ErrorCode.EXISTING_MEMBER) {
                throw new GlobalException(ErrorCode.EXISTING_MEMBER); // 또는 사용자에게 직접 전달
            } else {
                log.error(e.getMessage());
                throw new GlobalException(ErrorCode.CERTIFICATION_FAILED);
            }
        }

      return null;
    }




    public  TokenDTO signInProcess(MemberSignInRequest request) {

        OIDCDecodePayload payload = null;

        try {
            payload = oidcService.certificatingIdToken(request.getIdToken());
            // 신규 회원이면 404 에러, 기존에 존재하는 회원이면 발급
            if(!memberRepository.existsByEmail(payload.getEmail())){
                throw new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
            }else{
                Member member = memberRepository.findByEmail(payload.getEmail()).orElse(null);

                if(member == null){
                    throw  new GlobalException(ErrorCode.MEMBER_NOT_FOUND);
                }

                String accessToken = jwtUtil.createToken(member, true);
                String refreshToken = jwtUtil.createToken(member, false);

                return TokenDTO.of(accessToken, refreshToken);
            }

        } catch (Exception e) {
            if (e instanceof GlobalException && ((GlobalException) e).getErrorCode() == ErrorCode.EXISTING_MEMBER) {
                throw new GlobalException(ErrorCode.EXISTING_MEMBER); // 또는 사용자에게 직접 전달
            } else {
                log.error(e.getMessage());
                throw new GlobalException(ErrorCode.CERTIFICATION_FAILED);
            }
        }
    }

    private SocialType checkSocialType (String socialType){
        return switch (socialType) {
            case "kakao", "KAKAO" -> SocialType.KAKAO;
            case "google", "GOOGLE" -> SocialType.GOOGLE;
            case "naver", "NAVER" -> SocialType.NAVER;
            default -> null;
        };
    }
}
