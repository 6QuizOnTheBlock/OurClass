package com.quiz.ourclass.domain.member.service;

import com.quiz.ourclass.domain.member.dto.OIDCDecodePayload;
import com.quiz.ourclass.domain.member.dto.TokenDTO;
import com.quiz.ourclass.domain.member.dto.request.DefaultImageRequest;
import com.quiz.ourclass.domain.member.dto.request.DeveloperAtRtRequest;
import com.quiz.ourclass.domain.member.dto.request.MemberSignInRequest;
import com.quiz.ourclass.domain.member.dto.request.MemberSignUpRequest;
import com.quiz.ourclass.domain.member.entity.DefaultImage;
import com.quiz.ourclass.domain.member.dto.request.UpdateFcmTokenRequest;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.entity.Role;
import com.quiz.ourclass.domain.member.entity.SocialType;
import com.quiz.ourclass.domain.member.repository.DefaultImageRepository;
import com.quiz.ourclass.domain.member.repository.MemberRepository;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.AwsS3ObjectStorage;
import com.quiz.ourclass.global.util.jwt.JwtUtil;
import java.util.Objects;
import java.util.Optional;
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
    private final DefaultImageRepository defaultImageRepository;
    private final RedisTemplate<String, Object> redisTemplate;


    public TokenDTO signUpProcess(MemberSignUpRequest request) {

        String imgUrl;

        long num = request.getDefaultImage();

        if (num == 0) {
            imgUrl = awsS3ObjectStorage.uploadFile(request.getFile());
        } else {
            imgUrl = Objects.requireNonNull(defaultImageRepository.findById(num).orElse(null))
                .getPhoto();
        }

        return Optional.ofNullable(request.getIdToken())
            .map(oidcService::certificatingIdToken) // ID 토큰 검증
            .map(payload -> memberRepository.findByEmail(payload.getEmail())
                .map(
                    member -> updateExistingMember(member, imgUrl, request.getRole())) // 기존 멤버 업데이트
                .orElseGet(() -> registerNewMember(payload, imgUrl, request.getRole()))) // 새 멤버 등록
            .orElseThrow(() -> new GlobalException(ErrorCode.CERTIFICATION_FAILED)); // 검증 실패 예외 처리
    }

    private TokenDTO updateExistingMember(Member member, String imgUrl, String role) {
        if (member.getProfileImage() == null || member.getRole() == null || member.getRole()
            .equals(Role.GUEST)) {
            Member.addInfo(member, imgUrl, role);
            memberRepository.save(member);
            return createTokenDTO(member); // 또는 업데이트된 멤버에 대한 새로운 토큰 반환
        } else {
            throw new GlobalException(ErrorCode.EXISTING_MEMBER);
        }
    }

    // Member 등록 후 접근 토큰, 갱신 토큰 출력
    private TokenDTO registerNewMember(OIDCDecodePayload payload, String imgUrl, String role) {
        Member newMember = memberRepository.save(
            Member.of(payload.getEmail(), payload.getNickname(), SocialType.KAKAO, imgUrl, role));
        return createTokenDTO(newMember);
    }


    public TokenDTO signInProcess(MemberSignInRequest request) {
        return Optional.ofNullable(
                request.getIdToken())                                            // Optional.ofNullable -> null 이 나오면 바로 종료
            .map(
                oidcService::certificatingIdToken)                                                 // ID 토큰 검증
            .map(payload -> memberRepository.findByEmail(
                    payload.getEmail())                        // 이메일로 멤버 조회
                .orElseThrow(() -> new GlobalException(
                    ErrorCode.MEMBER_NOT_FOUND)))                // 멤버가 없으면 예외 발생
            .map(
                this::createTokenDTO)                                                              // 토큰 생성 및 반환
            .orElseThrow(() -> new GlobalException(
                ErrorCode.CERTIFICATION_FAILED));                // 검증 실패 예외 처리
    }

    // 접근 토큰, 갱신 토큰 만들기
    private TokenDTO createTokenDTO(Member member) {
        String accessToken = jwtUtil.createToken(member, true);
        String refreshToken = jwtUtil.createToken(member, false);
        jwtUtil.saveRefresh(member.getId(), refreshToken);
        return TokenDTO.of(accessToken, refreshToken,
            member.getRole().equals(Role.TEACHER) ? "TEACHER" : "STUDENT");
    }


    public TokenDTO giveDeveloperAccessToken(DeveloperAtRtRequest request) {

        return Optional.ofNullable(request.getEmail())
            .flatMap(memberRepository::findByEmail) // 이제 올바르게 Optional<Member>를 다룹니다.
            .map(this::createTokenDTO) // 멤버가 존재하면 토큰 생성
            .orElseThrow(() -> new GlobalException(
                ErrorCode.MEMBER_NOT_FOUND)); // 멤버가 없거나 토큰 생성이 실패했을 때 예외 처리

    }

    private SocialType checkSocialType(String socialType) {
        return switch (socialType) {
            case "kakao", "KAKAO" -> SocialType.KAKAO;
            case "google", "GOOGLE" -> SocialType.GOOGLE;
            case "naver", "NAVER" -> SocialType.NAVER;
            default -> null;
        };
    }

    public DefaultImage updateDefaultImage(DefaultImageRequest request) {
        String imgUrl = awsS3ObjectStorage.uploadFile(request.getFile());
        DefaultImage image = defaultImageRepository.findById(request.getId()).orElse(null);

        if (image == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND_DEFAULT_IMAGE);
        }

        image.setPhoto(imgUrl);
        return defaultImageRepository.save(image);

    }

    public void updateFcmToken(UpdateFcmTokenRequest request) {
        ValueOperations<String, Object> value = redisTemplate.opsForValue();
    }


}
