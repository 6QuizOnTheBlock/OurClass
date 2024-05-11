package com.sixkids.teacher.board.post.postlist

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.domain.usecase.post.GetPostListUseCase
import com.sixkids.model.Post
import com.sixkids.model.PostCategory
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
    private val getPostListUseCase: GetPostListUseCase,
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase
): BaseViewModel<PostState, PostEffect>(PostState()){

    private var organizationId: Int? = null

    var postList: Flow<PagingData<Post>>? = null

    fun getPostList() {
        viewModelScope.launch {
            intent { copy(isLoding = true) }

            if (organizationId == null){
                organizationId = getSelectedOrganizationIdUseCase().getOrNull()
            }

            if (organizationId != null){
                postList = getPostListUseCase(
                    organizationId = organizationId!!,
                    postCategory = PostCategory.FREE
                ).cachedIn(viewModelScope)
            } else {
                postSideEffect(PostEffect.OnShowSnackBar("학급 정보를 불러오지 못했어요 ;("))
            }

            intent { copy(isLoding = false) }
        }
    }
}