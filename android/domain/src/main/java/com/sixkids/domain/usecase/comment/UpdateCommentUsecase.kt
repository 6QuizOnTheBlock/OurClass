package com.sixkids.domain.usecase.comment

import com.sixkids.domain.repository.CommentRepository
import javax.inject.Inject

class UpdateCommentUsecase @Inject constructor(
    private val commentRepository: CommentRepository
){
    suspend operator fun invoke(
        commentId: Long, content: String
    ) = runCatching {
        commentRepository.updateComment(commentId, content)
    }
}