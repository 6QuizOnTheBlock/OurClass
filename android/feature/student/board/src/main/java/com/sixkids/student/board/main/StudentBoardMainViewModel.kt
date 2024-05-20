package com.sixkids.student.board.main

import android.util.Log
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
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "D107"
@HiltViewModel
class StudentBoardMainViewModel @Inject constructor(
    private val getPostListUseCase: GetPostListUseCase,
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase
): BaseViewModel<StudentBoardMainState,StudentBoardMainEffect>(StudentBoardMainState()) {
    private var organizationId: Int? = null

    var postList: Flow<PagingData<Post>>? = null

    fun getPostList() {
        viewModelScope.launch {
            intent { copy(isLoding = true) }

            if (organizationId == null){
                organizationId = getSelectedOrganizationIdUseCase().getOrNull()
                Log.d(TAG, "getPostList1: $organizationId")
            }

            Log.d(TAG, "getPostList: 2")

            if (organizationId != null){
                Log.d(TAG, "getPostList:3 ")
                postList = getPostListUseCase(
                    organizationId = organizationId!!,
                    postCategory = PostCategory.FREE
                ).cachedIn(viewModelScope).catch {
                    Log.d(TAG, "getPostList4: ${it.message}")
                }
            } else {
                postSideEffect(StudentBoardMainEffect.OnShowSnackBar("학급 정보를 불러오지 못했어요 ;("))
            }

            intent { copy(isLoding = false) }
        }
    }
}