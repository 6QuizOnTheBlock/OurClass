package com.quiz.ourclass.domain.chat.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * AhoCorasick Class 트라이 구조와 실패 링크를 사용하여 여러 금지어를 효율적으로 검색할 수 있는 알고리즘
 */
public class AhoCorasick {

    /**
     * TreeNode 는 트라이의 각 노드를 나타내며, 자식 노드, 실패 링크, 그리고 단어의 끝을 표시하는 필드를 가짐
     */
    static class TreeNode {

        Map<Character, TreeNode> children = new HashMap<>();
        TreeNode fail;
        boolean endOfWord = false;
        String word; // 해당 노드에서 끝나는 금지어

        TreeNode() {
            fail = null;
            endOfWord = false;
            word = null;
        }
    }

    // 트라이의 루트 노드
    TreeNode root;


    // 루트 노드 초기화
    public AhoCorasick() {
        root = new TreeNode();
    }

    /**
     * 주어진 금지어를 트라이에 삽입
     *
     * @param word 트라이에 삽입할 금지어
     */
    public void insert(String word) {
        // 초기 노드 설정 (root 시작)
        TreeNode current = root;
        // 입력된 단어를 문자 단위로 순회
        for (char wordChar : word.toCharArray()) {
            // 현재 노드(current)의 자식 중에서 wordChar 라는 키를 가진 노드가 있는지 확인
            // 자식 노드가 존재하지 않으면, new TreeNode 인스턴스를 생성하여 이를 자식 노드로 추가
            // 존재하면 해당 키의 값을 반환
            current = current.children.computeIfAbsent(wordChar, character -> new TreeNode());
        }
        current.endOfWord = true;
        // 해당 노드에서 끝나는 전체 단어를 저장
        current.word = word;
    }

    /**
     * 트라이 구조에 실패 링크를 구축함 실패 링크는 패턴 매칭이 실패했을 때 다음 검색 시작 노드를 가르킴
     */
    public void buildFailureLinks() {
        // 레벨별로 노드를 탐색하기 위한 큐를 초기화
        Queue<TreeNode> queue = new LinkedList<>();
        // 루트 노드의 모든 자식 노드를 큐에 추가하고
        // 각 자식의 실패 링크를 루트 노드로 설정
        // 루트 노드의 자식들은 검색이 실패하면 루트로 돌아가야 하기 때문
        for (TreeNode child : root.children.values()) {
            child.fail = root;
            queue.add(child);
        }

        while (!queue.isEmpty()) {
            TreeNode current = queue.poll();

            // 현재 노드의 모든 자식 노드를 반복 처리
            for (Map.Entry<Character, TreeNode> entry : current.children.entrySet()) {
                char ch = entry.getKey(); // 현재 자식 노드의 문자를 가져옴
                TreeNode child = entry.getValue(); // 현재 자식 노드를 가져옴
                TreeNode f = current.fail; // 현재 노드의 실패 링크를 가져옴

                // 실패 링크를 따라가면서 현재 문자를 자식으로 가지는 노드를 찾는다
                while (f != null && !f.children.containsKey(ch)) {
                    // 해당 문자가 없으면 계속 상위 실패 링크를 따라감
                    f = f.fail;
                }
                // 실패 링크를 찾은 경우 그 노드의 자식을 현재 자식 노드의 실패 링크로 설정
                // 찾지 못한 경우 루트를 실패 링크로 설정
                child.fail = (f == null) ? root : f.children.get(ch);

                // 만약 실패 링크가 있는 노드가 단어의 끝이면 현재 자식 노드도 단어의 끝으로 표시
                if (child.fail != null && child.fail.endOfWord) {
                    child.endOfWord = true;
                }

                // 처리한 자식 노드를 큐에 추가해서 그 자식의 실패 링크도 설정할 수 있도록 함
                queue.add(child);
            }
        }
    }

    /**
     * 주어진 텍스트에서 금지어를 검색
     *
     * @param text 검색할 텍스트
     * @return 검색된 금지어 리스트
     */
    public List<String> search(String text) {
        List<String> results = new ArrayList<>();
        TreeNode current = root; // 현재 노드를 루트로 초기화
        int index = 0; // Text 인덱스

        // Text 끝까지 반복
        while (index < text.length()) {
            char ch = text.charAt(index); // 현재 인덱스의 문자

            // 현재 노드에 해당 문자의 자식이 없으면 실패 링크를 따라감
            while (current != null && !current.children.containsKey(ch)) {
                current = current.fail;
            }

            // 실패 링크를 따라갔을 때 null 되면 루트에서 다시 시작
            if (current == null) {
                current = root;
                index++;
                continue;
            }

            // 해당 문자가 자식에 있으면 해당 자식 노드로 이동
            current = current.children.get(ch);

            // 현재 노드가 단어의 끝이면 결과 리스트에 추가
            if (current != null && current.endOfWord) {
                // 금지어 시작 인덱스 계산
                int start = index - current.word.length() + 1;

                // 실제 금지어를 결과 리스트에 추가
                results.add(text.substring(start, index + 1));
            }
            index++;
        }
        return results;
    }
}

