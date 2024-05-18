package com.sixkids.teacher.managestudent.detail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.GetMemberRelationUseCase
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.domain.usecase.organization.GetStudentDetailUseCase
import com.sixkids.domain.usecase.organization.GetStudentRelationUseCase
import com.sixkids.teacher.managestudent.navigation.ManageStudentRoute.STUDENT_ID_NAME
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "D107"
@HiltViewModel
class ManageStudentDetailViewModel @Inject constructor(
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase,
    private val getStudentDetailUseCase: GetStudentDetailUseCase,
    private val getMemberRelationUseCase: GetMemberRelationUseCase,
    private val getStudentRelationUseCase: GetStudentRelationUseCase,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ManageStudentDetailState, ManageStudentDetailEffect>(ManageStudentDetailState()){
    private val studentId = savedStateHandle.get<Long>(STUDENT_ID_NAME)
    private var orgId = -1

    fun initData(){
        viewModelScope.launch {
            getSelectedOrganizationIdUseCase().onSuccess {
                orgId= it
                getDetail(it)
                getRelation(it)
            }.onFailure {
                postSideEffect(
                    ManageStudentDetailEffect.HandleException(it, ::initData)
                )
            }
        }
    }
    private fun getDetail(orgId: Int){
        viewModelScope.launch {
            getStudentDetailUseCase(orgId.toLong(), studentId!!).onSuccess {member ->
                intent { copy(memberDetail = member) }
            }.onFailure {
                postSideEffect(
                    ManageStudentDetailEffect.HandleException(it, ::initData)
                )
            }
        }
    }
    private fun getRelation(orgId: Int){
        viewModelScope.launch {
            getMemberRelationUseCase(orgId.toLong(), studentId!!, null).onSuccess {relationList ->
                intent { copy(studentList = relationList) }
            }.onFailure {
                postSideEffect(
                    ManageStudentDetailEffect.HandleException(it, ::initData)
                )
            }
        }
    }

    fun onFriendClick(targetStudentId: Long) {
        Log.d(TAG, "onFriendClick: $targetStudentId, $studentId")
        viewModelScope.launch {
            getStudentRelationUseCase(orgId.toLong(), studentId!!, targetStudentId)
                .onSuccess {
                    Log.d(TAG, "onFriendClick: $it")
                    intent {
                        copy(
                            isDialogShowing = true,
                            relation = it
                        )
                    }
                }.onFailure {
                    //todo
                }
        }
    }

    fun cancelDialog() = intent { copy(isDialogShowing = false) }

}