package com.sixkids.student.board.write

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.organization.GetSelectedOrganizationIdUseCase
import com.sixkids.domain.usecase.post.NewPostUseCase
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class StudentBoardWriteViewModel @Inject constructor(
    private val newPostUseCase: NewPostUseCase,
    private val getSelectedOrganizationIdUseCase: GetSelectedOrganizationIdUseCase
): BaseViewModel<StudentBoardWriteState, StudentBoardWriteEffect>(StudentBoardWriteState()){

    private var organizationId: Int? = null

    fun onBack() = postSideEffect(StudentBoardWriteEffect.NavigateBack)
    fun onTitleChanged(title: String) = intent { copy(title = title) }
    fun onContentChanged(content: String) = intent { copy(content = content) }
    fun onAnonymousChecked(checked: Boolean) = intent { copy(anonymousChecked = checked) }
    fun onAddPhoto(bitmap: Bitmap) = intent { copy(photo = bitmap) }
    fun showToast(message: String) = postSideEffect(StudentBoardWriteEffect.OnShowSnackbar(message))

    fun onPost(photo: File?) {
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
                    file = photo
                ).onSuccess {
                    postSideEffect(StudentBoardWriteEffect.OnShowSnackbar("게시글 작성에 성공했어요 :)"))
                    postSideEffect(StudentBoardWriteEffect.NavigateBack)
                }.onFailure {
                    postSideEffect(StudentBoardWriteEffect.OnShowSnackbar(it.message ?: "게시글 작성에 실패했어요 ;("))
                }
            } else {
                postSideEffect(StudentBoardWriteEffect.OnShowSnackbar("학급 정보를 불러오지 못했어요 ;("))
            }

            intent { copy(isLoading = false) }
        }
    }
}