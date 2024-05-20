package com.sixkids.domain.usecase.comment

import com.sixkids.domain.repository.CommentRepository
import javax.inject.Inject

class ReportCommentUseCase @Inject constructor(
    private val commentRepository: CommentRepository
){
    suspend operator fun invoke(commentId: Long) = runCatching {
        commentRepository.reportComment(commentId)
    }
}