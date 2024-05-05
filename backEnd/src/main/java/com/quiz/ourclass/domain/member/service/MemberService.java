package com.quiz.ourclass.domain.member.service;

import com.quiz.ourclass.domain.member.dto.OIDCDecodePayload;
import com.quiz.ourclass.domain.member.dto.TokenDTO;
import com.quiz.ourclass.domain.member.dto.request.DefaultImageRequest;
import com.quiz.ourclass.domain.member.dto.request.DeveloperAtRtRequest;
import com.quiz.ourclass.domain.member.dto.request.MemberSignInRequest;
import com.quiz.ourclass.domain.member.dto.request.MemberSignUpRequest;
import com.quiz.ourclass.domain.member.dto.request.UpdateFcmTokenRequest;
import com.quiz.ourclass.domain.member.dto.response.DefaultImagesResponse;
import com.quiz.ourclass.domain.member.dto.response.MemberMeResponse;
import com.quiz.ourclass.domain.member.entity.DefaultImage;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.entity.Role;
import com.quiz.ourclass.domain.member.entity.SocialType;
import com.quiz.ourclass.domain.member.mapper.DefaultImageMapper;
import com.quiz.ourclass.domain.member.mapper.MemberMeMapper;
import com.quiz.ourclass.domain.member.repository.DefaultImageRepository;
import com.quiz.ourclass.domain.member.repository.MemberRepository;
import com.quiz.ourclass.global.exception.ErrorCode;
import com.quiz.ourclass.global.exception.GlobalException;
import com.quiz.ourclass.global.util.AwsS3ObjectStorage;
import com.quiz.ourclass.global.util.RedisUtil;
import com.quiz.ourclass.global.util.UserAccessUtil;
import com.quiz.ourclass.global.util.jwt.JwtUtil;
import java.time.Duration;
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
    private final RedisUtil redisUtil;
    private final UserAccessUtil userAccessUtil;
    private final DefaultImageRepository defaultImageRepository;
    private final MemberMeMapper memberMeMapper;
    private final DefaultImageMapper defaultImageMapper;


    public TokenDTO signUpProcess(MemberSignUpRequest request) {

        String imgUrl;

        long num = request.getDefaultImage();

        if (num == 0) {
            imgUrl = awsS3ObjectStorage.uploadFile(request.getFile());
        } else {
            imgUrl = Objects.requireNonNull(defaultImageRepository.findById(num).orElse(null))
                .getPhoto();
        }

        // a) ID 토큰 검증
        // b) 기존 멤버 업데이트 (위의 chaining 에서 Optional 이 비어있으면 실행되지 않는다.)
        // c) 새 멤버 등록 -> orElseGet Null일 때만 발동!
        // d) 검증 실패 예외 처리
        return Optional.ofNullable(request.getIdToken())
            .map(oidcService::certificatingIdToken)
            .map(payload -> memberRepository.findByEmail(payload.getEmail())
                .map(member -> updateExistingMember(member, imgUrl, request.getRole()))
                .orElseGet(() -> registerNewMember(payload, imgUrl, request.getRole())))
            .orElseThrow(() -> new GlobalException(ErrorCode.CERTIFICATION_FAILED));
    }

    private TokenDTO updateExistingMember(Member member, String imgUrl, Role role) {
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
    private TokenDTO registerNewMember(OIDCDecodePayload payload, String imgUrl, Role role) {
        Member newMember = memberRepository.save(
            Member.of(payload.getEmail(), payload.getNickname(), SocialType.KAKAO, imgUrl, role));
        return createTokenDTO(newMember);
    }


    /*
     *  로그인 프로세스 (chaining 설명)
     *  1) Optional.ofNullable -> null 이 나오면 바로 종료
     *  2) ID 토큰 검증
     *  3) 이메일로 멤버 조회
     *  4) 멤버가 없으면 예외 발생
     *  5) 토큰 생성 및 반환
     *  6) 검증 실패 시 예외 처리
     * */
    public TokenDTO signInProcess(MemberSignInRequest request) {
        return Optional.ofNullable(
                request.getIdToken())
            .map(oidcService::certificatingIdToken)
            .map(payload -> memberRepository.findByEmail(payload.getEmail())
                .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND)))
            .map(this::createTokenDTO)
            .orElseThrow(() -> new GlobalException(ErrorCode.CERTIFICATION_FAILED));
    }

    // 접근 토큰, 갱신 토큰 만들기 -> 토큰을 만들 멤버에 대한 검증을 끝냈다.
    private TokenDTO createTokenDTO(Member member) {
        String accessToken = jwtUtil.createToken(member.getId(), member.getRole().name(), true);
        String refreshToken = jwtUtil.createToken(member.getId(), member.getRole().name(), false);
        jwtUtil.saveRefresh(member.getId(), accessToken, refreshToken);
        return TokenDTO.of(accessToken, refreshToken,
            member.getRole().equals(Role.TEACHER) ? "TEACHER" : "STUDENT");
    }


    public TokenDTO giveDeveloperAccessToken(DeveloperAtRtRequest request) {

        return Optional.ofNullable(request.getEmail())
            .flatMap(memberRepository::findByEmail)
            .map(this::createTokenDTO)
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));

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

    public void saveFcmToken(UpdateFcmTokenRequest request) {
        Member member = userAccessUtil.getMember();
        long id = member.getId();
        String key = "FCM_" + id;
        String value = request.fcmToken();
        Duration twoMonths = Duration.ofDays(60); // 2달
        redisUtil.valueSet(key, value, twoMonths);
    }

    public DefaultImagesResponse getDefaultImages() {
        return defaultImageMapper.toDefaultImages(defaultImageRepository.findAll());
    }


    public MemberMeResponse rememberMe() {
        return Optional.ofNullable(userAccessUtil.getMember())
            .map(memberMeMapper::toMemberMeResponse)
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
    }


}
