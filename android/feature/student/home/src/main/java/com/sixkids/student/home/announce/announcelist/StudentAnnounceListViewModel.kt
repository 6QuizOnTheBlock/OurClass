package com.sixkids.student.home.announce.announcelist

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.domain.usecase.organization.LoadSelectedOrganizationNameUseCase
import com.sixkids.domain.usecase.post.GetPostListUseCase
import com.sixkids.model.Post
import com.sixkids.model.PostCategory
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentAnnounceListViewModel @Inject constructor(
    private val getPostListUseCase: GetPostListUseCase,
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase,
    private val loadSelectedOrganizationNameUseCase: LoadSelectedOrganizationNameUseCase
): BaseViewModel<StudentAnnounceListState, StudentAnnounceListEffect>(StudentAnnounceListState()){
    private var organizationId: Int? = null

    var postList: Flow<PagingData<Post>>? = null

    fun getAnnounceList() {
        viewModelScope.launch {
            intent { copy(isLoding = true) }

            loadSelectedOrganizationNameUseCase().onSuccess {
                intent { copy(classString = it) }
            }.onFailure {
                intent { copy(classString = "") }
            }

            if (organizationId == null){
                organizationId = getSelectedOrganizationIdUseCase().getOrNull()
            }

            if (organizationId != null){
                postList = getPostListUseCase(
                    organizationId = organizationId!!,
                    postCategory = PostCategory.NOTICE
                ).cachedIn(viewModelScope)
            } else {
                postSideEffect(StudentAnnounceListEffect.OnShowSnackBar("학급 정보를 불러오지 못했어요 ;("))
            }

            intent { copy(isLoding = false) }
        }
    }
}