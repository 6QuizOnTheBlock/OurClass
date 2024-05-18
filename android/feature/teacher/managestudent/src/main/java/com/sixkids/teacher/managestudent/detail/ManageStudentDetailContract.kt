package com.sixkids.teacher.managestudent.detail

import com.sixkids.model.MemberDetail
import com.sixkids.model.MemberSimpleWithScore
import com.sixkids.model.StudentRelation
import com.sixkids.ui.base.SideEffect
import com.sixkids.ui.base.UiState

data class ManageStudentDetailState(
    val memberDetail: MemberDetail = MemberDetail(),
    val studentList: List<MemberSimpleWithScore> = emptyList(),
    val relation: StudentRelation = StudentRelation(),
    val isDialogShowing: Boolean = false,
): UiState

sealed interface ManageStudentDetailEffect : SideEffect{
    data class NavigateToChallenge(val studentId: Long) : ManageStudentDetailEffect
    data class NavigateToRelay(val studentId: Long) : ManageStudentDetailEffect
    data class NavigateToPost(val studentId: Long) : ManageStudentDetailEffect
    data class HandleException(val throwable: Throwable, val retry: () -> Unit) : ManageStudentDetailEffect
}