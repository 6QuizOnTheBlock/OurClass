package com.sixkids.teacher.challenge.create.matching

import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.GetOrganizationMemberUseCase
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.ui.base.BaseViewModel
import com.sixkids.ui.extension.flatMap
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GroupMatchingSettingViewModel @Inject constructor(
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase,
    private val getOrganizationMemberUseCase: GetOrganizationMemberUseCase,
) : BaseViewModel<GroupMatchingSettingState, GroupMatchingSettingEffect>(
    GroupMatchingSettingState()
) {

    private var orgId = 0L
    fun initData() {
        viewModelScope.launch {
            getSelectedOrganizationIdUseCase()
                .flatMap { orgId ->
                    this@GroupMatchingSettingViewModel.orgId = orgId.toLong()
                    getOrganizationMemberUseCase(orgId)
                }
                .onSuccess { memberList ->
                    intent {
                        copy(studentList = memberList)
                    }
                }
        }
    }

    fun removeStudent(memberId: Long) {
        intent {
            copy(studentList = studentList.filter { it.id != memberId })
        }
    }

    fun moveNextStep() {
        postSideEffect(
            GroupMatchingSettingEffect.MoveToMatchingSuccessStep(
                matchingMemberList = uiState.value.studentList.map { it.id },
                matchingType = uiState.value.matchingType
            )
        )
    }

}
