package com.quiz.ourclass.domain.chat.algorithm;

import com.quiz.ourclass.domain.chat.entity.ChatFilter;
import com.quiz.ourclass.domain.chat.repository.ChatFilterRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Filter {

    private final AhoCorasick ac;

    /**
     * ChatFilterRepository 통해 금지어 목록을 가져와 Aho-Corasick 트라이를 구성
     *
     * @param chatFilterRepository 데이터베이스에서 금지어를 조회하는 데 사용될 JPA 리포지토리
     */
    @Autowired
    public Filter(ChatFilterRepository chatFilterRepository) {
        List<ChatFilter> bannedWords = chatFilterRepository.findAll();

        // AhoCorasick 객체 생성
        ac = new AhoCorasick();

        // 금지어 리스트를 반복하여 각 금지어를 AhoCorasick 트라이에 삽입
        for (ChatFilter word : bannedWords) {
            ac.insert(word.getBadWord());
        }

        // 실패 링크를 구축하여 AhoCorasick 알고리즘의 초기 설정을 완료
        ac.buildFailureLinks();
    }

    /**
     * 입력된 메시지에서 금지어를 찾아 치환
     *
     * @param message 사용자로부터 입력받은 원본 메시지
     * @return 치환된 메시지
     */
    public String filterMessage(String message) {
        // 메시지를 정규화하여 비문자 제거
        String normalizedMessage = normalize(message);

        // 정규화된 메시지에서 금지어 검색
        List<String> results = ac.search(normalizedMessage);

        // 원본 메시지를 기반으로 StringBuilder 객체 생성
        StringBuilder sb = new StringBuilder(message);

        // 정규화된 메시지의 각 문자가 원본 메시지에서 어디에 위치하는지를 저장할 배열 초기화
        int[] map = new int[normalizedMessage.length()];

        // 원본 메시지를 순회하면서 각 문자의 위치를 map 배열에 저장
        int normIndex = 0;
        for (int i = 0; i < message.length(); i++) {
            // 유효한 단어나 숫자만을 추출
            if (Character.isLetterOrDigit(message.charAt(i))) {
                map[normIndex++] = i;
            }
        }

        // 검색된 금지어의 각 인스턴스에 대해 원본 메시지에서의 위치를 찾아 *로 치환
        for (String word : results) {
            int normStartIndex = normalizedMessage.indexOf(word);
            while (normStartIndex != -1) {
                // 금지어 시작과 끝 인덱스를 원본 메시지에서 찾음
                int originalStart = map[normStartIndex];
                int originalEnd = map[normStartIndex + word.length() - 1];

                // 시작 인덱스부터 끝 인덱스까지의 문자를 *로 치환
                for (int i = originalStart; i <= originalEnd; i++) {
                    sb.setCharAt(i, '*');
                }

                // 다음 금지어 인스턴스 찾기
                normStartIndex = normalizedMessage.indexOf(word, normStartIndex + word.length());
            }
        }
        return sb.toString();
    }

    /**
     * 메시지에서 비문자(non-letter/non-digit)를 제거하여 정규화합니다.
     *
     * @param message 원본 메시지
     * @return 정규화된 메시지
     */
    private String normalize(String message) {
        return message.replaceAll("[^\\p{L}\\p{Nd}]+", "");
    }
}
