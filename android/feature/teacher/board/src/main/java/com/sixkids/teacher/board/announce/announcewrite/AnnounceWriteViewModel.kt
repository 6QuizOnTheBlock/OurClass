package com.sixkids.teacher.board.announce.announcewrite

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.domain.usecase.post.NewPostUseCase
import com.sixkids.model.PostCategory
import com.sixkids.teacher.board.post.postwrite.PostWriteEffect
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class AnnounceWriteViewModel @Inject constructor(
    private val newPostUseCase: NewPostUseCase,
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase
): BaseViewModel<AnnounceWriteState, AnnounceWriteEffect>(AnnounceWriteState()){

    private var organizationId: Int? = null

    fun onBack() = postSideEffect(AnnounceWriteEffect.NavigateBack)
    fun onTitleChanged(title: String) = intent { copy(title = title) }
    fun onContentChanged(content: String) = intent { copy(content = content) }
    fun onAddPhoto(bitmap: Bitmap) = intent { copy(photo = bitmap) }
    fun showToast(message: String) = postSideEffect(AnnounceWriteEffect.OnShowSnackbar(message))

    fun onPostAnnounce(photo: File?) {
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
                    postCategory = PostCategory.NOTICE,
                    file = photo
                ).onSuccess {
                    postSideEffect(AnnounceWriteEffect.OnShowSnackbar("알림장 작성에 성공했어요 :)"))
                    postSideEffect(AnnounceWriteEffect.NavigateBack)
                }.onFailure {
                    postSideEffect(AnnounceWriteEffect.OnShowSnackbar(it.message ?: "알림장 작성에 실패했어요 ;("))
                }
            } else {
                postSideEffect(AnnounceWriteEffect.OnShowSnackbar("학급 정보를 불러오지 못했어요 ;("))
                postSideEffect(AnnounceWriteEffect.NavigateBack)
            }

            intent { copy(isLoading = false) }
        }
    }
}