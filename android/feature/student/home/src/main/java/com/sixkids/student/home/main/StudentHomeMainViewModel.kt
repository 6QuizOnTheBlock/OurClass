package com.sixkids.student.home.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.domain.usecase.user.GetStudentHomeInfoUseCase
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "D107"
@HiltViewModel
class StudentHomeMainViewModel @Inject constructor(
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase,
    private val getStudentHomeInfoUseCase: GetStudentHomeInfoUseCase
) : BaseViewModel<StudentHomeMainState, StudentHomeMainEffect>(StudentHomeMainState()) {

    private var organizationId: Long? = null

    fun getStudentHomeInfo() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }

            if (organizationId == null) {
                organizationId = getSelectedOrganizationIdUseCase().getOrNull()?.toLong()
            }

            if (organizationId != null) {
                getStudentHomeInfoUseCase(organizationId!!)
                    .onSuccess { info ->
                        intent {
                            copy(
                                studentName = info.name,
                                studentImageUrl = info.photo,
                                studentClass = info.className,
                                studentExp = info.exp,
                                bestFriendList = info.relations
                            )
                        }
                    }
                    .onFailure {
                        postSideEffect(
                            StudentHomeMainEffect.onShowSnackBar(
                                it.message ?: "알 수 없는 오류가 발생했습니다."
                            )
                        )
                    }
            } else {
                postSideEffect(StudentHomeMainEffect.onShowSnackBar("학급 정보를 불러오지 못했어요 ;("))
            }


        }
    }

    fun showGreetingDialog(){
        intent { copy(isShowGreetingDialog = true) }
    }

    fun offDialog(){
        Log.d(TAG, "offDialog: ")
        intent { copy(isShowGreetingDialog = false) }
    }
}