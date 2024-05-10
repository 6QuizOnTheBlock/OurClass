package com.sixkids.domain.usecase.comment

import com.sixkids.domain.repository.CommentRepository
import javax.inject.Inject

class NewRecommentUseCase @Inject constructor(
    private val commentRepository: CommentRepository
){
    suspend operator fun invoke(
        postId: Long, content: String, parentId: Long
    ) = runCatching {
        commentRepository.createComment(postId, content, parentId)
    }
}