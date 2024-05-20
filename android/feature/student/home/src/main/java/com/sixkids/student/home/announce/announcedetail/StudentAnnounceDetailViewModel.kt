package com.sixkids.student.home.announce.announcedetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.sixkids.domain.usecase.comment.DeleteCommentUseCase
import com.sixkids.domain.usecase.comment.NewCommentUseCase
import com.sixkids.domain.usecase.comment.NewRecommentUseCase
import com.sixkids.domain.usecase.comment.ReportCommentUseCase
import com.sixkids.domain.usecase.comment.UpdateCommentUsecase
import com.sixkids.domain.usecase.post.DeletePostUseCase
import com.sixkids.domain.usecase.post.GetPostDetailUseCase
import com.sixkids.domain.usecase.post.UpdatePostUseCase
import com.sixkids.student.home.navigation.StudentHomeRoute
import com.sixkids.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentAnnounceDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getPostDetailUseCase: GetPostDetailUseCase,
    private val deleteCommentUseCase: DeleteCommentUseCase,
    private val updateCommentUsecase: UpdateCommentUsecase,
    private val newCommentUseCase: NewCommentUseCase,
    private val newRecommentUseCase: NewRecommentUseCase,
    private val reportCommentUseCase: ReportCommentUseCase
): BaseViewModel<StudentAnnounceDetailState, StudentAnnounceDetailEffect>(StudentAnnounceDetailState()){

    private val postId: Long = savedStateHandle.get<Long>(StudentHomeRoute.announceDetailARG)!!

    fun onCommentTextChanged(commentText: String) = intent { copy(commentText = commentText) }
    fun onSelectedCommentId(commentId: Long?) = intent {
        if (currentState.selectedCommentId == commentId) {
            copy(selectedCommentId = null)
        } else {
            copy(selectedCommentId = commentId)
        }
    }

    fun getAnnounceDetail() {
        viewModelScope.launch {
            intent { copy(isLoading = true) }
            getPostDetailUseCase(postId).onSuccess {
                intent { copy(postDetail = it) }
            }.onFailure {
                postSideEffect(StudentAnnounceDetailEffect.OnShowSnackbar(it.message ?: "게시글을 불러오지 못했어요"))
            }
            intent { copy(isLoading = false) }
        }
    }

    fun onNewComment() {
        if (currentState.commentText.isBlank()) {
            postSideEffect(StudentAnnounceDetailEffect.OnShowSnackbar("댓글을 입력해주세요"))
        } else {
            if (currentState.selectedCommentId == null) {
                viewModelScope.launch {
                    intent { copy(isLoading = true) }
                    newCommentUseCase(
                        postId = postId,
                        content = currentState.commentText,
                    ).onSuccess {
                        postSideEffect(StudentAnnounceDetailEffect.OnShowSnackbar("댓글이 작성되었습니다"))
                        intent { copy(commentText = "", selectedCommentId = null) }
                        getAnnounceDetail()
                    }.onFailure {
                        postSideEffect(
                            StudentAnnounceDetailEffect.OnShowSnackbar(
                                it.message ?: "댓글 작성에 실패했어요"
                            )
                        )
                    }
                    intent { copy(isLoading = false) }
                }
            } else {
                viewModelScope.launch {
                    intent { copy(isLoading = true) }
                    newRecommentUseCase(
                        postId = postId,
                        content = currentState.commentText,
                        currentState.selectedCommentId!!
                    ).onSuccess {
                        postSideEffect(StudentAnnounceDetailEffect.OnShowSnackbar("댓글이 작성되었습니다"))
                        intent { copy(commentText = "", selectedCommentId = null)}
                        getAnnounceDetail()
                    }.onFailure {
                        postSideEffect(
                            StudentAnnounceDetailEffect.OnShowSnackbar(
                                it.message ?: "댓글 작성에 실패했어요"
                            )
                        )
                    }
                    intent { copy(isLoading = false) }
                }
            }
        }
    }
}