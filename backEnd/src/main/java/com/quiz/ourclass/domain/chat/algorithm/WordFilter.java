package com.quiz.ourclass.domain.chat.algorithm;

import com.quiz.ourclass.domain.chat.entity.ChatFilter;
import com.quiz.ourclass.domain.chat.repository.ChatFilterRepository;
import com.quiz.ourclass.domain.organization.entity.Organization;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WordFilter {

    private final ChatFilterRepository chatFilterRepository;

    /**
     * Version - 2 정규식
     **/
    public String regexFilter(String message, Organization organization) {
        List<ChatFilter> chatFilters =
            chatFilterRepository.findByOrganization(organization);

        // 모든 공백과 특수 문자 제거하고 소문자로 변환하여 검사용 문자열 생성
        String sanitizedMessage =
            message.replaceAll("[^\\p{L}]", "").toLowerCase();

        for (ChatFilter chatFilter : chatFilters) {
            String bannedWord = chatFilter.getBadWord();

            // 단어 사이 비문자 허용 정규식 생성
            String regex = createRegexForWord(bannedWord);
            // regex 정규 표현식 기준으로 Pattern 객체를 생성
            Pattern pattern = Pattern.compile(regex);
            // 검사용 문자열 내에서 패턴에 매칭되는 부분을 찾기 위한 Matcher 객체를 생성
            Matcher matcher = pattern.matcher(sanitizedMessage);

            if (matcher.find()) {
                // 가장 최근의 매칭 연산에서 성공적으로 매칭된 부분 문자열 길이만큼 "*" 문자열 생성
                String starts = "*".repeat(matcher.group().length());
                message = message.replaceAll(regex, starts);
            }
        }

        return message;
    }

    private String createRegexForWord(String word) {
        int wordLength = word.length();
        StringBuilder regex = new StringBuilder();
        // 한 문자씩 추출해서 검사 후 다음 문자 뒤에 추가
        for (int i = 0; i < wordLength; i++) {
            regex.append(
                Pattern.quote(String.valueOf(word.charAt(i)))
            );
            if (i < wordLength - 1) {
                // 문자 사이에 어떠한 비문자 요소가 들어간 경우도 필터링함
                regex.append("[^\\p{L}]*");
            }
        }
        return regex.toString();
    }

    /**
     * Version - 1 슬라이딩 윈도우
     **/
    public String slidingWindowFilter(String message, Organization organization) {
        List<ChatFilter> chatFilters =
            chatFilterRepository.findByOrganization(organization);

        StringBuilder sb = new StringBuilder();
        List<String> words = new ArrayList<>();

        StringTokenizer stk = new StringTokenizer(message, " ");
        while (stk.hasMoreTokens()) {
            words.add(stk.nextToken());
        }
        for (String word : words) {
            sb.append(applySlidingWindow(word, chatFilters)).append(" ");
        }
        return sb.toString().trim();
    }

    private String applySlidingWindow(String word, List<ChatFilter> chatFilters) {
        int wordLength = word.length();

        for (int len = 1; len <= wordLength; len++) {
            for (int st = 0; st <= wordLength - len; st++) {
                String subWord = word.substring(st, st + len);

                for (ChatFilter chatFilter : chatFilters) {
                    String bannedWord = chatFilter.getBadWord();
                    if (subWord.equalsIgnoreCase(bannedWord)) { // 대소문자 구분 X
                        // 단어 수 만큼 * 채우기
                        String starts = "*".repeat(subWord.length());
                        // 대소문자 구분하지 않고, 문자열 내 특수 문자를 이스케이프 처리
                        return word.replaceAll("(?i)" + Pattern.quote(subWord), starts);
                    }
                }
            }
        }
        return word;
    }
}
