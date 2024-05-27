package com.quiz.ourclass.domain.challenge.algorithm;

import com.quiz.ourclass.domain.challenge.dto.response.AutoGroupMatchingResponse;
import com.quiz.ourclass.domain.member.mapper.MemberMapper;
import com.quiz.ourclass.domain.member.repository.MemberRepository;
import com.quiz.ourclass.domain.organization.entity.Relationship;
import com.quiz.ourclass.domain.organization.repository.RelationshipRepository;
import com.quiz.ourclass.global.dto.MemberSimpleDTO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.math3.ml.clustering.CentroidCluster;
import org.apache.commons.math3.ml.clustering.DoublePoint;
import org.apache.commons.math3.ml.clustering.KMeansPlusPlusClusterer;
import org.ejml.dense.row.decomposition.eig.SwitchingEigenDecomposition_DDRM;
import org.ejml.simple.SimpleMatrix;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class FriendlyGroup {

    private final RelationshipRepository relationshipRepository;
    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    // 클래스 변수로 memberIds 선언
    private List<Long> memberIds;

    /**
     * 조직의 학생들을 친밀도에 따라 그룹으로 나누는 메서드입니다. Spectral Clustering 알고리즘을 사용하여 그룹을 생성하고,
     * <p>
     * 각 그룹의 최소 및 최대 크기를 유지합니다.
     *
     * @param organizationId   그룹화할 학생들이 속한 조직의 ID
     * @param studentsPerGroup 각 그룹에 포함될 학생 수
     * @param memberIds        그룹화할 학생들의 ID 리스트
     * @return 그룹화된 학생들의 정보를 담은 AutoGroupMatchingResponse 리스트
     */
    @Transactional
    public List<AutoGroupMatchingResponse> makeFriendlyGroup(long organizationId,
        int studentsPerGroup, List<Long> memberIds) {

        // 클래스 변수로 memberIds 를 설정
        this.memberIds = memberIds;

        // 학생 ID를 인덱스로 매핑하는 맵 생성
        Map<Long, Integer> studentIdToIndex = new HashMap<>();
        memberIds.forEach(id -> studentIdToIndex.put(id, studentIdToIndex.size()));

        // 친밀도 매트릭스 계산
        double[][] adjacencyMatrix = precomputeRelationshipMatrix(organizationId, memberIds,
            studentIdToIndex);

        // 그룹의 수 계산
        int numGroups = (int) Math.ceil((double) memberIds.size() / studentsPerGroup);

        // Spectral Clustering 을 사용하여 그룹 생성
        List<List<Long>> groups = spectralClustering(adjacencyMatrix, numGroups, memberIds.size());

        // 학생이 그룹에 중복 배치되지 않도록 재배치
        redistributeMembers(groups);

        // 결과 로그 출력
        logGroupResults(groups, adjacencyMatrix, studentIdToIndex);

        // 그룹화된 학생들에 대한 응답 생성
        return buildResponse(groups);
    }


    /**
     * 주어진 조직의 학생들 간의 친밀도 점수를 기반으로 관계 매트릭스를 생성하는 메서드입니다.
     *
     * @param organizationId   관계를 조회할 조직의 ID
     * @param members          관계 매트릭스를 계산할 학생들의 ID 리스트
     * @param studentIdToIndex 학생 ID를 인덱스로 매핑하는 맵
     * @return 학생들 간의 친밀도 점수를 저장한 2차원 배열 (관계 매트릭스)
     */
    private double[][] precomputeRelationshipMatrix(long organizationId, List<Long> members,
        Map<Long, Integer> studentIdToIndex) {

        // 주어진 조직 ID와 학생 ID 리스트를 기반으로 모든 관계 데이터를 조회
        List<Relationship> relationships = relationshipRepository.findAllByOrganizationIdAndMemberIds(
            organizationId, members);

        // 학생 수에 맞는 2차원 배열(매트릭스)을 초기화
        double[][] matrix = new double[members.size()][members.size()];

        // 각 관계에 대해 친밀도 점수를 매트릭스에 설정
        relationships.forEach(relationship -> {
            // 관계의 첫 번째 학생의 인덱스를 조회
            Integer index1 = studentIdToIndex.get(relationship.getMember1().getId());
            // 관계의 두 번째 학생의 인덱스를 조회
            Integer index2 = studentIdToIndex.get(relationship.getMember2().getId());
            // 두 학생의 인덱스가 모두 존재하는 경우에만 매트릭스에 값을 설정
            if (index1 != null && index2 != null) {
                // 첫 번째 학생과 두 번째 학생 간의 친밀도 점수를 매트릭스에 설정
                matrix[index1][index2] = relationship.getRelationPoint();
                // 대칭성 유지: 두 번째 학생과 첫 번째 학생 간의 친밀도 점수를 동일하게 설정
                matrix[index2][index1] = relationship.getRelationPoint();
            }
        });
        // 생성된 관계 매트릭스를 반환
        return matrix;
    }


    /**
     * 스펙트럼 클러스터링을 사용하여 주어진 인접 행렬로부터 학생들을 그룹으로 나누는 메서드입니다.
     *
     * @param adjacencyMatrix 학생들 간의 관계를 나타내는 인접 행렬
     * @param numClusters     생성할 그룹(클러스터)의 수
     * @param n               전체 학생 수
     * @return 학생들을 그룹으로 나눈 결과 리스트
     */
    private List<List<Long>> spectralClustering(
        double[][] adjacencyMatrix, int numClusters, int n) {
        // 학위 행렬 계산
        double[][] degreeMatrix = new double[n][n];
        for (int i = 0; i < n; i++) {
            double sum = 0;
            for (int j = 0; j < n; j++) {
                sum += adjacencyMatrix[i][j];
            }
            degreeMatrix[i][i] = sum;
        }

        // 라플라시안 행렬 계산: L = D - A
        SimpleMatrix L = new SimpleMatrix(degreeMatrix).minus(new SimpleMatrix(adjacencyMatrix));

        // 고유값 및 고유벡터 계산
        // SwitchingEigenDecomposition_DDRM 클래스의 인스턴스를 생성
        // 첫 번째 매개변수 n은 행렬의 크기를 나타내며, 두 번째 매개변수는 대칭 여부를 나타낸다.
        // 세 번째 매개변수는 고유값 분해의 허용 오차를 설정함
        SwitchingEigenDecomposition_DDRM eightDecompose =
            new SwitchingEigenDecomposition_DDRM(n, true, 1e-6);

        // 생성한 eightDecompose 인스턴스를 사용하여 라플라시안 행렬 L의 고유값 분해를 수행
        // eightDecompose.decompose 메서드는 행렬의 고유값과 고유벡터를 계산
        eightDecompose.decompose(L.getMatrix());

        // 고유값 정렬 및 해당 고유벡터 가져오기
        // 고유값을 저장할 리스트를 초기화
        List<Double> eigenValues = new ArrayList<>();

        // 고유벡터를 고유값과 매핑하여 저장할 맵을 초기화
        Map<Double, SimpleMatrix> eigenVectorsMap = new HashMap<>();

        // 고유값 분해 결과로부터 고유값과 고유벡터를 추출
        for (int i = 0; i < n; i++) {
            // 현재 인덱스 i의 고유값을 가져오기
            double eigValue = eightDecompose.getEigenvalue(i).getReal();
            // 현재 인덱스 i의 고유벡터를 가져오기
            SimpleMatrix eigVector = SimpleMatrix.wrap(eightDecompose.getEigenVector(i));
            // 고유값 리스트에 현재 고유값을 추가
            eigenValues.add(eigValue);
            // 고유값을 키로, 해당 고유벡터를 값으로 하여 맵에 추가
            eigenVectorsMap.put(eigValue, eigVector);
        }

        // 고유값 리스트를 오름차순으로 정렬
        Collections.sort(eigenValues);

        // 가장 작은 numClusters 개의 고유벡터로부터 행렬 구성
        // 'features' 라는 새로운 행렬을 생성
        // 이 행렬의 크기는 n x numClusters 이며,
        // 여기서 n은 데이터 포인트의 수, numClusters 는 클러스터의 수이다.
        SimpleMatrix features = new SimpleMatrix(n, numClusters);

        // 가장 작은 고유값에 해당하는 고유벡터들을 'features' 행렬에 삽입
        // 'eigenVectorsMap' 에서 가장 작은 고유값에 해당하는 고유벡터를 가져와 'features' 행렬의 열에 삽입
        for (int i = 0; i < numClusters; i++) {
            // 고유값을 오름차순으로 정렬했으므로, 'eigenValues.get(i)'는 가장 작은 고유값 중 i번째 값입니다.
            // 이 고유값에 해당하는 고유벡터를 'eigenVectorsMap' 에서 가져와 'features' 행렬의 i번째 열에 삽입
            features.insertIntoThis(0, i, eigenVectorsMap.get(eigenValues.get(i)));
        }


        /* 각 포인트를 단위 길이로 정규화하여 KMeans 클러스터링을 수행하기 위한 준비 단계 */

        // 행을 단위 길이로 정규화
        // 각 행을 단위 길이로 정규화하기 위해 'features' 행렬의 모든 행에 대해 반복
        for (int i = 0; i < n; i++) {
            // 'features' 행렬의 i번째 행을 벡터로 추출하여 그 노름(norm)을 계산
            double norm = features.extractVector(true, i).normF();
            // i번째 행의 각 요소를 해당 행의 노름으로 나누어 단위 길이로 만든다.
            for (int j = 0; j < numClusters; j++) {
                // 'features' 행렬의 i번째 행, j번째 열의 요소를 그 노름으로 나누어 정규화한다.
                features.set(i, j, features.get(i, j) / norm);
            }
        }


        /* KMeans++를 사용하여 데이터를 클러스터로 분할하는 단계 */

        // KMeans 를 사용하여 정규화된 행을 클러스터링
        // KMeans++ 클러스터러를 초기화합니다. 클러스터의 수는 numClusters 이다.
        // KMeans++는 초기 클러스터 중심을 더 신뢰성 있게 선택하기 위한 방법
        KMeansPlusPlusClusterer<DoublePoint> kMeans = new KMeansPlusPlusClusterer<>(numClusters);

        // 클러스터링을 위한 입력 데이터를 저장할 리스트를 초기화
        List<DoublePoint> clusterInput = new ArrayList<>();

        // 각 데이터를 클러스터링을 위한 형식으로 변환
        for (int i = 0; i < n; i++) {
            // 'features' 행렬의 i번째 행을 복사하여 'row' 배열에 저장
            // 여기서 'numClusters' 는 각 데이터 포인트의 차원이다.
            double[] row = new double[numClusters];
            for (int j = 0; j < numClusters; j++) {
                // 'features' 행렬의 i번째 행, j번째 열의 값을 'row' 배열에 저장
                row[j] = features.get(i, j);
            }
            // 'row' 배열을 'DoublePoint' 객체로 변환하여 'clusterInput' 리스트에 추가
            clusterInput.add(new DoublePoint(row));
        }
        // 'clusterInput' 데이터를 사용하여 KMeans++ 클러스터링을 수행합니다.
        // 결과는 클러스터의 리스트
        List<CentroidCluster<DoublePoint>> clusters = kMeans.cluster(clusterInput);

        // 클러스터를 원래 학생 ID로 매핑
        // 그룹 리스트를 초기화한다. 각 그룹은 학생 ID의 리스트이다.
        List<List<Long>> groups = new ArrayList<>();

        // 학생이 할당되었는지 추적하는 불리언 배열을 초기화합니다. 배열의 크기는 학생 수임
        boolean[] assigned = new boolean[memberIds.size()];

        // 학생의 할당 상태를 추적하는 집합을 초기화
        Set<Long> assignedMembers = new HashSet<>();

        // 각 클러스터에 대해 반복
        for (CentroidCluster<DoublePoint> cluster : clusters) {
            // 현재 클러스터에 대한 그룹을 초기화
            List<Long> group = new ArrayList<>();

            // 클러스터에 속한 각 포인트에 대해 반복
            for (DoublePoint point : cluster.getPoints()) {
                // 포인트의 인덱스를 clusterInput 리스트에서 찾기
                int index = clusterInput.indexOf(point);

                // 인덱스가 유효하고, 해당 인덱스의 학생이 아직 할당되지 않았으며,
                // 해당 학생이 할당된 적이 없는 경우에만 그룹에 추가
                if (index != -1 && !assigned[index] && !assignedMembers.contains(
                    memberIds.get(index))) {
                    // 학생 ID를 그룹에 추가
                    group.add(memberIds.get(index));

                    // 해당 인덱스와 학생 ID를 할당된 것으로 표시
                    assigned[index] = true;
                    assignedMembers.add(memberIds.get(index));
                }
            }
            // 완성된 그룹을 그룹 리스트에 추가
            groups.add(group);
        }
        // 완성된 그룹 반환
        return groups;
    }


    /**
     * 주어진 그룹 리스트를 기반으로 학생들을 재배치하여 최소 및 최대 그룹 크기 요구사항을 충족시킵니다.
     *
     * @param groups 학생 ID로 구성된 그룹 리스트
     */
    private void redistributeMembers(List<List<Long>> groups) {
        // 총 클러스터 수를 계산합니다.
        int numClusters = groups.size();

        // 총 학생 수를 계산합니다.
        int totalStudents = memberIds.size();

        // 최소 그룹 크기를 계산합니다.
        int minGroupSize = totalStudents / numClusters;

        // 최대 그룹 크기를 계산합니다.
        int maxGroupSize = (int) Math.ceil((double) totalStudents / numClusters);

        // 할당된 학생을 추적하기 위한 집합을 초기화합니다.
        Set<Long> assignedStudents = new HashSet<>();

        // 할당되지 않은 학생을 추적하기 위한 리스트를 초기화합니다.
        List<Long> unassignedStudents = new ArrayList<>();

        // 초기 할당된 학생 집합을 설정하고 할당되지 않은 학생을 수집합니다.
        for (List<Long> group : groups) {
            for (Long student : group) {
                if (assignedStudents.contains(student)) {
                    // 이미 할당된 학생이 그룹에 있으면 unassignedStudents 리스트에 추가합니다.
                    unassignedStudents.add(student);
                } else {
                    // 처음 할당된 학생은 assignedStudents 집합에 추가합니다.
                    assignedStudents.add(student);
                }
            }
        }

        // 모든 그룹이 최소 크기 요구사항을 충족하도록 보장합니다.
        for (List<Long> group : groups) {
            while (group.size() < minGroupSize && !unassignedStudents.isEmpty()) {
                // 그룹 크기가 최소 크기보다 작고 할당되지 않은 학생이 있으면
                // unassignedStudents 리스트에서 학생을 제거하고 그룹에 추가합니다.
                Long student = unassignedStudents.removeFirst();
                group.add(student);
                assignedStudents.add(student);
            }
        }

        // 최대 그룹 크기를 초과하는 그룹에서 초과 멤버를 재배치합니다.
        for (List<Long> group : groups) {
            while (group.size() > maxGroupSize) {
                // 그룹 크기가 최대 크기보다 크면 그룹의 마지막 학생을 제거하고
                // unassignedStudents 리스트에 추가합니다.
                Long student = group.removeLast();
                unassignedStudents.add(student);
                assignedStudents.remove(student);
            }
        }

        // 남아있는 할당되지 않은 학생들을 공간이 있는 그룹에 배치합니다.
        for (List<Long> group : groups) {
            while (group.size() < maxGroupSize && !unassignedStudents.isEmpty()) {
                // 그룹 크기가 최대 크기보다 작고 할당되지 않은 학생이 있으면
                // unassignedStudents 리스트에서 학생을 제거하고 그룹에 추가합니다.
                Long student = unassignedStudents.removeFirst();
                group.add(student);
                assignedStudents.add(student);
            }
        }
    }


    /**
     * 주어진 그룹 목록을 기반으로 AutoGroupMatchingResponse 객체의 리스트를 빌드합니다.
     *
     * @param groups 학생 ID로 구성된 그룹 리스트
     * @return 각 그룹에 해당하는 AutoGroupMatchingResponse 객체의 리스트
     */
    private List<AutoGroupMatchingResponse> buildResponse(List<List<Long>> groups) {
        // 결과 응답을 저장할 리스트를 초기화
        List<AutoGroupMatchingResponse> responses = new ArrayList<>();

        // 각 그룹에 대해 반복
        for (List<Long> group : groups) {
            // 현재 그룹에 속한 멤버들의 정보를 저장할 리스트를 초기화
            List<MemberSimpleDTO> members = group.stream()
                // 멤버 ID를 사용하여 데이터베이스에서 멤버 객체를 조회
                .map(id -> memberRepository.findById(id).orElse(null))
                // 조회된 멤버 객체가 null 이 아닌 경우에만 필터링
                .filter(Objects::nonNull)
                // 멤버 객체를 MemberSimpleDTO 객체로 변환
                .map(memberMapper::memberToMemberSimpleDTO)
                // 변환된 DTO 객체들을 리스트로 수집
                .collect(Collectors.toList());

            // 현재 그룹의 멤버 리스트와 멤버 수를 포함한 응답 객체를 생성
            responses.add(new AutoGroupMatchingResponse(members, members.size()));
        }

        // 모든 그룹에 대한 응답 리스트를 반환
        return responses;
    }


    /**
     * 각 그룹의 구성원과 해당 그룹의 총 친밀도 점수를 로그에 기록합니다.
     *
     * @param groups             각 그룹의 학생 ID 리스트
     * @param relationshipMatrix 학생 간의 친밀도 점수를 저장한 행렬
     * @param studentIdToIndex   학생 ID를 배열 인덱스로 매핑하는 맵
     */
    private void logGroupResults(List<List<Long>> groups,
        double[][] relationshipMatrix,
        Map<Long, Integer> studentIdToIndex) {

        // 모든 그룹에 대해 반복
        for (int i = 0; i < groups.size(); i++) {
            // 현재 그룹을 가져온다.
            List<Long> group = groups.get(i);

            // 현재 그룹의 구성원들을 로그에 기록
            log.info("Group {}: {}", i + 1, group);

            // 그룹의 총 친밀도 점수를 저장할 변수를 초기화
            double groupScore = 0.0;

            // 그룹 내 모든 학생 쌍에 대해 반복
            for (int j = 0; j < group.size(); j++) {
                for (int k = j + 1; k < group.size(); k++) {
                    // 두 학생의 인덱스를 가져온다.
                    Integer index1 = studentIdToIndex.get(group.get(j));
                    Integer index2 = studentIdToIndex.get(group.get(k));

                    // 인덱스가 null이 아닌 경우 친밀도 점수를 추가
                    if (index1 != null && index2 != null) {
                        groupScore += relationshipMatrix[index1][index2];
                    }
                }
            }
            // 현재 그룹의 총 친밀도 점수를 로그에 출력
            log.info("Group {} Score: {}", i + 1, groupScore);
        }
    }
}
