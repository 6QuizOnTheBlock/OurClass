package com.sixkids.teacher.board.postwrite

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.domain.usecase.post.NewPostUseCase
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostWriteViewModel @Inject constructor(
    private val newPostUseCase: NewPostUseCase,
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase
): BaseViewModel<PostWriteState, PostWriteEffect>(PostWriteState()){

    private var organizationId: Int? = null

    fun onBack() = postSideEffect(PostWriteEffect.NavigateBack)
    fun onTitleChanged(title: String) = intent { copy(title = title) }
    fun onContentChanged(content: String) = intent { copy(content = content) }
    fun onAnonymousChecked(checked: Boolean) = intent { copy(anonymousChecked = checked) }
    fun onAddPhoto(bitmap: Bitmap) = intent { copy(photo = bitmap) }
    fun showToast(message: String) = postSideEffect(PostWriteEffect.OnShowSnackbar(message))

    fun onPost() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }

            if (organizationId == null) {
                organizationId = getSelectedOrganizationIdUseCase().getOrNull()
            }

            if (organizationId != null) {
                newPostUseCase(
                    organizationId = organizationId!!.toLong(),
                    title = currentState.title,
                    content = currentState.content,
                    secretStatus = currentState.anonymousChecked,
                    postCategory = "FREE",
                    file = null
                ).onSuccess {
                    postSideEffect(PostWriteEffect.OnShowSnackbar("게시글 작성에 성공했어요 :)"))
                    postSideEffect(PostWriteEffect.NavigateBack)
                }.onFailure {
                    postSideEffect(PostWriteEffect.OnShowSnackbar(it.message ?: "게시글 작성에 실패했어요 ;("))
                }
            } else {
                postSideEffect(PostWriteEffect.OnShowSnackbar("학급 정보를 불러오지 못했어요 ;("))
            }

            intent { copy(isLoading = false) }
        }
    }
}