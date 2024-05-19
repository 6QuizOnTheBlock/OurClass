package com.quiz.ourclass.domain.challenge.algorithm;

import com.quiz.ourclass.domain.organization.entity.Relationship;
import com.quiz.ourclass.domain.organization.repository.MemberOrganizationRepository;
import com.quiz.ourclass.domain.organization.repository.RelationshipRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ojalgo.optimisation.Expression;
import org.ojalgo.optimisation.ExpressionsBasedModel;
import org.ojalgo.optimisation.Variable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class FriendlyGroup {

    private final RelationshipRepository relationshipRepository;
    private final MemberOrganizationRepository memberOrganizationRepository;

    // 이 메소드는 트랜잭션을 지원하는 설정에서 읽기 전용으로 실행됩니다.
    // 읽기 전용이기 때문에 데이터를 변경할 수 없고,
    // Propagation.SUPPORTS 설정은 현재 진행 중인 트랜잭션이 있을 경우 그 트랜잭션에 참여하며,
    // 없을 경우 비트랜잭션 상태로 실행됩니다.
    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public void solveGroupingProblem(long organizationId, int studentsPerGroup) {
        // 주어진 조직 ID에 해당하는 학생 수를 데이터베이스에서 조회
        int totalStudentCount = memberOrganizationRepository.countByOrganizationId(organizationId);

        // 조직 ID에 따라 모든 관계 데이터를 조회
        List<Relationship> relationships = relationshipRepository.findAllByOrganizationIdOrderByMember1(
            organizationId);

        // 학생 ID를 배열 인덱스로 매핑하기 위한 HashMap을 생성
        Map<Long, Integer> studentIdToIndex = new HashMap<>();

        // 현재 인덱스를 추적하기 위한 변수
        int currentIndex = 0;
        for (Relationship relationship : relationships) {
            // 각 관계에 대해 두 멤버의 ID를 맵에 없으면 추가하고, 인덱스를 증가
            if (!studentIdToIndex.containsKey(relationship.getMember1().getId())) {
                studentIdToIndex.put(relationship.getMember1().getId(), currentIndex++);
            }
            if (!studentIdToIndex.containsKey(relationship.getMember2().getId())) {
                studentIdToIndex.put(relationship.getMember2().getId(), currentIndex++);
            }
        }

        // 학생 간의 관계 점수를 저장할 2차원 배열을 초기화
        double[][] relationshipMatrix = new double[totalStudentCount][totalStudentCount];
        for (Relationship relationship : relationships) {
            int index1 = studentIdToIndex.get(relationship.getMember1().getId());
            int index2 = studentIdToIndex.get(relationship.getMember2().getId());
            relationshipMatrix[index1][index2] = relationship.getRelationPoint();
            relationshipMatrix[index2][index1] = relationship.getRelationPoint(); // 대칭성 유지
        }

        // 필요한 그룹 수를 계산합니다. 총 학생 수를 그룹당 학생 수로 나누어 올림 처리
        int numGroups = (int) Math.ceil((double) totalStudentCount / studentsPerGroup);

        // 최적화 모델을 생성
        ExpressionsBasedModel model = new ExpressionsBasedModel();

        // 각 학생이 각 그룹에 할당되는 변수를 2차원 배열로 초기화
        Variable[][] x = new Variable[totalStudentCount][numGroups];
        for (int i = 0; i < totalStudentCount; i++) {
            for (int j = 0; j < numGroups; j++) {
                // 변수를 이진 변수로 선언하여, 학생이 특정 그룹에 속하는지를 나타냅니다.
                x[i][j] = Variable.make("x[" + i + "][" + j + "]").binary();

                // 모델에 변수를 추가
                model.addVariable(x[i][j]);
            }
        }

        // 제약 조건 추가
        applyConstraints(model, x, numGroups, totalStudentCount, studentsPerGroup);

        // 목표 함수 설정
        setObjectiveFunction(model, x, relationshipMatrix, numGroups, totalStudentCount);

        // 모델 최적화
        model.maximise();

        // 결과 출력
        printSolution(x, numGroups, totalStudentCount, studentIdToIndex, relationshipMatrix);
    }

    /**
     * 모델에 필요한 제약 조건을 적용합니다. 이 메서드는 두 가지 주요 제약을 설정합니다: 1. 각 학생은 하나의 그룹에만 속해야 합니다. 2. 각 그룹은 최대 지정된 수의
     * 학생만 포함할 수 있습니다.
     *
     * @param model             최적화 모델 객체
     * @param x                 학생들이 그룹에 할당되는 상태를 나타내는 이진 변수 배열
     * @param numGroups         전체 그룹 수
     * @param totalStudentCount 전체 학생 수
     * @param studentsPerGroup  각 그룹에 할당될 수 있는 최대 학생 수
     */
    private void applyConstraints(ExpressionsBasedModel model, Variable[][] x, int numGroups,
        int totalStudentCount, int studentsPerGroup) {
        // 각 학생은 정확히 하나의 그룹에만 할당되어야 함을 보장하는 제약 조건을 설정합니다.
        // 이를 위해 모든 학생에 대해 '학생 제약 조건'을 추가합니다.
        for (int i = 0; i < totalStudentCount; i++) {
            // 학생 i가 정확히 한 그룹에만 속하도록 하는 제약식을 생성합니다.
            // 이 제약식은 각 학생 변수의 합이 정확히 1이 되도록 요구합니다.
            Expression studentConstraint = model.addExpression("studentConstraint" + i).lower(1)
                .upper(1);
            for (int j = 0; j < numGroups; j++) {
                // 각 그룹 j에 대해 학생 i가 그룹에 포함되면 1, 아니면 0의 값을 갖는 변수 x[i][j]를 설정합니다.
                studentConstraint.set(x[i][j], 1);
            }
        }

        // 각 그룹은 최대 studentsPerGroup 명을 포함해야 하는 제약 조건을 설정합니다.
        // 이를 위해 모든 그룹에 대해 '그룹 제약 조건'을 추가합니다.
        for (int j = 0; j < numGroups; j++) {
            // 그룹 j가 포함할 수 있는 최대 학생 수를 제한하는 제약식을 생성합니다.
            // 이 제약식은 각 그룹에 할당된 학생 수의 합이 studentsPerGroup을 초과하지 않도록 합니다.
            Expression groupConstraint = model.addExpression("groupConstraint" + j)
                .upper(studentsPerGroup);
            for (int i = 0; i < totalStudentCount; i++) {
                // 각 학생 i에 대해, 학생이 그룹 j에 속할 경우 변수 x[i][j]의 값을 1로 설정합니다.
                groupConstraint.set(x[i][j], 1);
            }
        }
    }

    /**
     * 최적화 모델의 목표 함수를 설정합니다. 이 함수는 학생들 간의 친밀도를 최대화하는 방향으로 그룹을 최적화하기 위해 정의됩니다. 각 그룹 내의 학생들 사이에서 친밀도
     * 점수를 기반으로 상호 작용 변수(interaction)를 계산하고, 이 변수를 목표 함수에 추가하여 그룹 내의 친밀도 합을 최대화합니다.
     *
     * @param model              최적화를 수행할 모델 객체
     * @param x                  학생들이 그룹에 할당되는 이진 변수 배열
     * @param relationshipMatrix 학생 간의 친밀도 점수가 저장된 행렬
     * @param numGroups          생성될 그룹의 총 수
     * @param totalStudentCount  전체 학생 수
     */
    private void setObjectiveFunction(ExpressionsBasedModel model, Variable[][] x,
        double[][] relationshipMatrix, int numGroups, int totalStudentCount) {
        // 목표 함수를 정의하고 가중치를 1.0으로 설정합니다.
        Expression objective = model.addExpression("objective").weight(1.0);

        // 모든 그룹에 대해 각 학생 쌍의 상호작용을 고려하여 친밀도를 계산합니다.
        for (int k = 0; k < numGroups; k++) {
            for (int i = 0; i < totalStudentCount; i++) {
                for (int j = i + 1; j < totalStudentCount; j++) {
                    // 각 학생 쌍과 그룹에 대한 상호 작용 변수를 생성하고 모델에 추가합니다.
                    // 이 변수는 두 학생이 동일한 그룹에 할당되었는지 여부를 나타냅니다
                    // (1이면 할당됨, 0이면 할당되지 않음).
                    Variable interaction = Variable.make(
                        "interaction[" + i + "][" + j + "][" + k + "]").binary();
                    model.addVariable(interaction);

                    // 두 학생 i와 j가 같은 그룹 k에 있을 때, interaction 변수가 1이 되도록 설정합니다.
                    // 이를 위해 x[i][k] + x[j][k] - 2 * interaction >= 0 이라는 제약식을 모델에 추가합니다.
                    // 여기서 x[i][k]와 x[j][k]는 각 학생이 특정 그룹에 할당되면 1의 값을 가집니다.
                    // 따라서 두 학생 모두 같은 그룹에 할당되었을 때만 (x[i][k] + x[j][k] == 2),
                    // interaction 은 1이 될 수 있습니다.
                    model.addExpression("interaction_constraint_" + i + "_" + j + "_" + k)
                        .set(x[i][k], 1)
                        .set(x[j][k], 1)
                        .set(interaction, -2)
                        .lower(0);

                    // interaction 변수가 1이 될 조건을 추가로 정의합니다. interaction 은 두 학생이 같은 그룹에 있지 않을 때 0이어야 합니다.
                    // interaction - x[i][k] <= 0 이라는 제약식은 interaction 이 1일 때, x[i][k]도 반드시 1이어야 함을 의미합니다.
                    // 즉, 학생 i가 그룹 k에 할당되지 않았다면, interaction 은 0이 될 수밖에 없습니다.
                    model.addExpression("interaction_constraint2_" + i + "_" + j + "_" + k)
                        .set(interaction, 1)
                        .set(x[i][k], -1)
                        .upper(0);

                    // 마찬가지로, interaction - x[j][k] <= 0 제약식은
                    // 학생 j가 그룹 k에 할당되지 않았을 경우 interaction 을 0으로 유지합니다.
                    // 이 조건은 상호 작용 변수가 두 학생이 실제로 같은 그룹에 할당된 경우에만 1의 값을 가지도록 보장합니다.
                    model.addExpression("interaction_constraint3_" + i + "_" + j + "_" + k)
                        .set(interaction, 1)
                        .set(x[j][k], -1)
                        .upper(0);

                    // 목표 함수에 각 상호 작용의 친밀도 점수를 반영합니다.
                    objective.set(interaction, relationshipMatrix[i][j]);
                }
            }
        }
    }

    /**
     * 최적화 모델의 결과를 출력하는 함수입니다. 각 그룹의 구성과 그룹별 친밀도 점수를 출력하여, 어떻게 학생들이 그룹화되었는지 보여줍니다.
     *
     * @param x                  각 학생이 각 그룹에 할당된 여부를 나타내는 변수 배열
     * @param numGroups          총 그룹의 수
     * @param totalStudentCount  조직 내의 총 학생 수
     * @param studentIdToIndex   학생 ID와 이를 배열 인덱스로 매핑하는 맵
     * @param relationshipMatrix 학생 간의 친밀도 점수를 저장한 행렬
     */
    private void printSolution(Variable[][] x, int numGroups, int totalStudentCount,
        Map<Long, Integer> studentIdToIndex, double[][] relationshipMatrix) {
        log.info("Solution:");
        double totalScore = 0; // 모든 그룹의 총 친밀도 점수를 저장할 변수

        // 각 그룹에 대해 반복
        for (int j = 0; j < numGroups; j++) {
            log.info("Group {} : ", (j + 1)); // 그룹 번호 출력
            double groupScore = 0; // 해당 그룹의 친밀도 점수를 계산할 변수

            // 해당 그룹에 속한 모든 학생에 대해 반복
            for (int i = 0; i < totalStudentCount; i++) {
                if (x[i][j].getValue().intValue() == 1) { // 학생 i가 그룹 j에 할당된 경우
                    long studentId = getKeyByValue(studentIdToIndex, i); // 학생 ID 조회
                    log.info("Student ID: {} ", studentId); // 학생 ID 출력

                    // 그룹 내 다른 학생들과의 친밀도 점수 합산
                    for (int k = 0; k < totalStudentCount; k++) {
                        // 다른 학생 k도 같은 그룹에 할당된 경우
                        if (i != k && x[k][j].getValue().intValue() == 1) {
                            // i와 k의 친밀도 점수를 groupScore에 더함
                            groupScore += relationshipMatrix[i][k];
                        }
                    }
                }
            }
            totalScore += groupScore; // 그룹 점수를 총 점수에 추가
            log.info(" Group Score: {}", groupScore); // 그룹별 친밀도 점수 출력
        }
        log.info("Total Score: {}", totalScore); // 모든 그룹의 총 친밀도 점수 출력
    }

    /**
     * 맵에서 주어진 값에 해당하는 키를 찾아 반환합니다.
     *
     * @param map   키와 값을 저장하고 있는 맵
     * @param value 찾고자 하는 값
     * @return 찾은 키, 없으면 null 반환
     */
    private <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }
}
