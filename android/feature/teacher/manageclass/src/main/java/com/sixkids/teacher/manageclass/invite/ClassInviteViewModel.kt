package com.sixkids.teacher.manageclass.invite

import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.GetOrganizaionInviteCodeUseCase
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassInviteViewModel @Inject constructor(
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase,
    private val getOrganizaionInviteCodeUseCase: GetOrganizaionInviteCodeUseCase
) : BaseViewModel<ClassInviteState, ClassInviteEffect>(ClassInviteState()) {
    private var organizationId: Int? = null

    private suspend fun getOrganizationId(): Int? {
        if (organizationId == null) {
            organizationId = getSelectedOrganizationIdUseCase().getOrNull()
        }
        return organizationId
    }

    fun getInviteCode() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }

            val orgId = getOrganizationId()
            if (orgId != null) {
                getOrganizaionInviteCodeUseCase(orgId)
                    .onSuccess {
                        intent { copy(classInviteCode = it)}
                    }
                    .onFailure {
                        postSideEffect(ClassInviteEffect.onShowSnackBar(it.message ?: "초대코드 생성에 실패했어요 ;("))
                    }
            } else {
                postSideEffect(ClassInviteEffect.onShowSnackBar("학급 정보를 불러오지 못했어요 ;("))
            }

            intent { copy(isLoading = false) }
        }
    }
}