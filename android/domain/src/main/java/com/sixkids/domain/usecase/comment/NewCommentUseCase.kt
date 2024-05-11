package com.sixkids.domain.usecase.comment

import com.sixkids.domain.repository.CommentRepository
import javax.inject.Inject

class NewCommentUseCase @Inject constructor(
    private val commentRepository: CommentRepository
){
    suspend operator fun invoke(
        postId: Long, content: String
    ) = runCatching {
        commentRepository.createComment(postId, content, 0L)
    }
}