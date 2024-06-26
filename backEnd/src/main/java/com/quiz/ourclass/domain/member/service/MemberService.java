package com.quiz.ourclass.domain.member.service;

import com.quiz.ourclass.domain.member.dto.OIDCDecodePayload;
import com.quiz.ourclass.domain.member.dto.TokenDTO;
import com.quiz.ourclass.domain.member.dto.request.DefaultImageRequest;
import com.quiz.ourclass.domain.member.dto.request.DeveloperAtRtRequest;
import com.quiz.ourclass.domain.member.dto.request.MemberSignInRequest;
import com.quiz.ourclass.domain.member.dto.request.MemberSignUpRequest;
import com.quiz.ourclass.domain.member.dto.request.MemberUpdateRequest;
import com.quiz.ourclass.domain.member.dto.request.UpdateFcmTokenRequest;
import com.quiz.ourclass.domain.member.dto.response.DefaultImagesResponse;
import com.quiz.ourclass.domain.member.dto.response.MemberMeResponse;
import com.quiz.ourclass.domain.member.dto.response.MemberUpdateResponse;
import com.quiz.ourclass.domain.member.entity.DefaultImage;
import com.quiz.ourclass.domain.member.entity.Member;
import com.quiz.ourclass.domain.member.entity.Role;
import com.quiz.ourclass.domain.member.entity.SocialType;
import com.quiz.ourclass.domain.member.mapper.MemberMapper;
import com.quiz.ourclass.domain.member.repository.DefaultImageRepository;
import com.quiz.ourclass.domain.member.repository.MemberRepository;
import com.quiz.ourclass.domain.member.repository.RefreshRepository;
import com.quiz.ourclass.domain.quiz.dto.request.QuizStartRequest;
import com.quiz.ourclass.domain.quiz.dto.response.QuizStartResponse;
import com.quiz.ourclass.global.dto.MemberSimpleDTO;
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
    private final MemberMapper memberMapper;
    private final RefreshRepository refreshRepository;


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
        Member member = userAccessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        long id = member.getId();
        String key = "FCM_" + id;
        String value = request.fcmToken();
        Duration twoMonths = Duration.ofDays(60); // 2달
        redisUtil.valueSet(key, value, twoMonths);
    }

    public DefaultImagesResponse getDefaultImages() {
        return memberMapper.toDefaultImages(defaultImageRepository.findAll());
    }


    public MemberMeResponse rememberMe() {
        return userAccessUtil.getMember()
            .map(memberMapper::toMemberMeResponse)
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
    }

    public MemberUpdateResponse updateProfile(MemberUpdateRequest request) {
        return userAccessUtil.getMember()
            .map(member -> updateMemberProfile(member, request))
            .map(memberMapper::toUpdateResponse)
            .orElseThrow(() -> new GlobalException(ErrorCode.AWS_SERVER_ERROR));
    }

    private Member updateMemberProfile(Member member, MemberUpdateRequest request) {

        if (request.defaultImage() == 0) {
            member.setProfileImage(awsS3ObjectStorage.uploadFile(request.file()));
        } else {
            member.setProfileImage(defaultImageRepository
                .findById(request.defaultImage())
                .orElseThrow(() -> new GlobalException(ErrorCode.AWS_SERVER_ERROR)).getPhoto());
        }
        return memberRepository.save(member);
    }


    public QuizStartResponse certificatingUser(QuizStartRequest request) {

        // 1. [UUID]를 이용해 해당 퀴즈 방이 살아있는 방인지 확인 합니다.
        return Optional.ofNullable(redisUtil.getQuizGame(request.uuid()))
            .map(quizGameId -> {
                // 2. UUID가 유효하면 이메일로 회원 정보를 조회합니다.
                return memberRepository.findByEmail(request.email())
                    .map(member -> {
                        // 3. 회원 정보가 있으면 JWT 토큰을 생성합니다.
                        String accessToken = jwtUtil.createToken(member.getId(),
                            member.getRole().name(), true);
                        // 4. 응답 객체 생성 후 반환
                        return memberMapper.memberToQuizStartResponse(Long.parseLong(quizGameId),
                            member, accessToken);
                    })
                    .orElseThrow(() -> new GlobalException(
                        ErrorCode.MEMBER_NOT_FOUND)); // 회원 정보가 없는 경우 예외 처리
            })
            .orElse(null); // [UUID]가 유효하지 않은 경우 null 반환
    }


    public void deleteMe() {
        // 1. 멤버 특정
        Member member = userAccessUtil.getMember()
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        // 2. 멤버 Access Token 삭제
        jwtUtil.deleteToken(member);
        // 3. 멤버 FCM Token 삭제
        redisUtil.delete("FCM_" + member.getId());
        // 4. 멤버 최종 삭제
        memberRepository.delete(member);

    }

    public MemberSimpleDTO select(Long id) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new GlobalException(ErrorCode.MEMBER_NOT_FOUND));
        return memberMapper.memberToMemberSimpleDTO(member);
    }
}
