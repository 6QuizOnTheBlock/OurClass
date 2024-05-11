package com.sixkids.student.group.join

import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.user.LoadUserInfoUseCase
import com.sixkids.model.MemberSimple
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class JoinGroupViewModel @Inject constructor(
    private val loadUserInfoUseCase: LoadUserInfoUseCase
) : BaseViewModel<JoinGroupState, JoinGroupEffect>(JoinGroupState()) {
    fun loadUserInfo() {
        viewModelScope.launch {
            loadUserInfoUseCase().onSuccess {
                intent {
                    copy(
                        member = MemberSimple(
                            id = it.id.toLong(),
                            name = it.name,
                            photo = it.photo
                        )
                    )
                }
            }.onFailure {
                postSideEffect(JoinGroupEffect.HandleException(it, ::loadUserInfo))
            }
        }
    }
}
