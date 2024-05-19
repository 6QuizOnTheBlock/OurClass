package com.sixkids.teacher.challenge.create

import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.challenge.CreateChallengeUseCase
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.model.ChallengeGroup
import com.sixkids.model.GroupSimple
import com.sixkids.teacher.challenge.create.grouptype.GroupType
import com.sixkids.teacher.challenge.create.matching.MatchingSource
import com.sixkids.teacher.challenge.create.matching.MatchingType
import com.sixkids.ui.SnackbarToken
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ChallengeCreateViewModel @Inject constructor(
    private val createChallengeUseCase: CreateChallengeUseCase,
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase
) : BaseViewModel<ChallengeCreateUiState, ChallengeCreateEffect>(
    ChallengeCreateUiState()
) {

    private var isFirstVisited: Boolean = true
    fun initData() {
        viewModelScope.launch {
            if (isFirstVisited.not()) return@launch
            isFirstVisited = false

            getSelectedOrganizationIdUseCase().onSuccess {
                intent {
                    copy(organizationId = it)
                }
            }.onFailure {
                postSideEffect(ChallengeCreateEffect.HandleException(it) { initData() })
            }
        }
    }

    private var title: String = ""
    private var content: String = ""
    private var startTime: LocalDateTime = LocalDateTime.now()
    private var endTime: LocalDateTime = LocalDateTime.now()
    private var point: String = ""
    private var headCount: String = ""
    private var matchingMemberList: List<Long> = emptyList()
    private var groupMatchingType: MatchingType = MatchingType.FRIENDLY
    private var groupType: GroupType = GroupType.FREE
    private var groupList: List<GroupSimple> = emptyList()

    fun createChallenge() {
        viewModelScope.launch {
            createChallengeUseCase(
                organizationId = uiState.value.organizationId,
                title = title,
                content = content,
                startTime = startTime,
                endTime = endTime,
                reward = point.toInt(),
                minCount = headCount.toInt(),
                groups = groupList
            ).onSuccess { challengeId ->
                postSideEffect(ChallengeCreateEffect.NavigateResult(challengeId, title))
            }.onFailure {
                onShowSnackbar(SnackbarToken("챌린지 생성에 실패했습니다."))
            }
        }
    }

    fun moveNextStep() {
        intent {
            when (step) {
                ChallengeCreateStep.INFO -> copy(step = ChallengeCreateStep.GROUP_TYPE)
                ChallengeCreateStep.GROUP_TYPE -> copy(step = ChallengeCreateStep.MATCHING_TYPE)
                ChallengeCreateStep.MATCHING_TYPE -> copy(step = ChallengeCreateStep.MATCHING_SUCCESS)
                ChallengeCreateStep.MATCHING_SUCCESS -> copy(step = ChallengeCreateStep.RESULT)
                else -> copy()
            }
        }
    }

    fun movePrevStep() {
        intent {
            when (step) {
                ChallengeCreateStep.INFO -> {
                    postSideEffect(ChallengeCreateEffect.NavigateUp)
                    copy()
                }

                ChallengeCreateStep.GROUP_TYPE -> copy(step = ChallengeCreateStep.INFO)
                ChallengeCreateStep.MATCHING_TYPE -> copy(step = ChallengeCreateStep.GROUP_TYPE)
                ChallengeCreateStep.MATCHING_SUCCESS -> copy(step = ChallengeCreateStep.MATCHING_TYPE)
                else -> copy()
            }
        }
    }


    fun onShowSnackbar(snackbarToken: SnackbarToken) {
        postSideEffect(ChallengeCreateEffect.ShowSnackbar(snackbarToken))
    }

    fun updateTitle(title: String) {
        this.title = title
    }

    fun updateContent(content: String) {
        this.content = content
    }

    fun updateStartTime(startTime: LocalDateTime) {
        this.startTime = startTime
    }

    fun updateEndTime(endTime: LocalDateTime) {
        this.endTime = endTime
    }

    fun updatePoint(point: String) {
        this.point = point
    }

    fun updateCount(count: String) {
        this.headCount = count
    }

    fun updateGroupType(groupType: GroupType) {
        this.groupType = groupType
    }

    fun updateMatchingMemberList(matchingMemberList: List<Long>) {
        this.matchingMemberList = matchingMemberList
    }

    fun updateMatchingType(matchingType: MatchingType) {
        this.groupMatchingType = matchingType
    }

    fun getMatchingGroupList(): MatchingSource {
        return MatchingSource(
            orgId = uiState.value.organizationId.toLong(),
            minCount = headCount.toInt(),
            matchingType = groupMatchingType,
            members = matchingMemberList
        )
    }

    fun updateGroupList(challengeGroups: List<ChallengeGroup>) {
        this.groupList = challengeGroups.map { group ->
            GroupSimple(
                headCount = group.headCount,
                leaderId = group.memberList.first().id,
                students = group.memberList.map { it.id }
            )
        }
    }

}
