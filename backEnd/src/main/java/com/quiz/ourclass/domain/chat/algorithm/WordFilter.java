package com.quiz.ourclass.domain.chat.algorithm;

import com.quiz.ourclass.domain.chat.entity.ChatFilter;
import com.quiz.ourclass.domain.chat.repository.ChatFilterRepository;
import com.quiz.ourclass.domain.organization.entity.Organization;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
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
