package com.quiz.ourclass.global.config;

import com.quiz.ourclass.global.dto.ResultResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/*
 * 컨트롤러 별 실행 시간 체크
 * */
@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AopConfig {

    private double beforeTime = 0L;
    private double afterTime = 0L;

    @Pointcut("execution(* com..controller..*(..))")
    public void ControllerMethod() {
    }

    // 컨트롤러 내의 모든 매소드에 대하여 Logging을 실행한다. 다만 어노테이션이 붙은 매소드는 실행하지 않는다.
    @Around("ControllerMethod() && !@annotation(com.quiz.ourclass.global.config.annotation.LogExclusion)")
    public Object logging(ProceedingJoinPoint pjp) throws Throwable {
        // 시작시간 check
        beforeTime = System.currentTimeMillis();
        // request 파라미터 값 가져오기
        Object[] args = pjp.getArgs();
        StringBuilder logMsg = new StringBuilder();
        // request 값 확인

        log.info("-----------> REQUEST <Header>: {} \n <Body>: {}({}) ={}"
            , getHeaderDetail()
            , pjp.getSignature().getDeclaringTypeName()
            , pjp.getSignature().getName()
            , logMsg);
        // 결과 확인
        ResponseEntity<ResultResponse<?>> result = (ResponseEntity<ResultResponse<?>>) pjp.proceed();
        // 끝시간 check
        afterTime = System.currentTimeMillis();
        log.info("-----------> RESPONSE : {}({}) = {} ({}ms)"
            , pjp.getSignature().getDeclaringTypeName(), pjp.getSignature().getName(),
            result.getBody().getData(),
            (afterTime - beforeTime) / 1000.0);

        return result;
    }

    private String getObjectDetails(Object arg) {
        StringBuilder details = new StringBuilder();
        // 파라미터의 클래스 Reflection 을 들고 와서 그 필드들을 하나 하나 까본다.
        Field[] fields = arg.getClass().getDeclaredFields();
        for (Field field : fields) {
            // 식별 타입이 뭔지 확인한다. private final 상수 선언 시 확인에서 제외한다.
            // private final 멤버 변수는 보안상 오류가 나므로 생략한다.
            int modifiers = field.getModifiers();
            if (Modifier.isPrivate(modifiers) && Modifier.isFinal(modifiers)) {
                continue;
            }
            // private 멤버 변수도 접근 할 수 있도록 허용
            field.setAccessible(true);
            try {
                details.append((field.getName())).append("=");
                details.append((field.get(arg))).append(", ");
            } catch (IllegalAccessException e) {
                throw new RuntimeException("특정 필드 접근에 실패했습니다.", e);
            }
        }

        if (details.length() > 2) {
            details.setLength(details.length() - 2);
        }

        return details.toString();
    }

    // Header 내용을 확인한다.
    private StringBuilder getHeaderDetail() {

        StringBuilder ans = new StringBuilder();

        ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attrs != null) {
            HttpServletRequest request = attrs.getRequest();
            ans.append(" 요청주소: ").append(request.getRequestURL().toString())
                .append(" 요청Method: ").append(request.getMethod())
                .append(" IP 주소: ").append(request.getRemoteAddr());


        } else {
            System.out.println("No HTTP request details available");
        }

        return ans;
    }

}

