package com.sixkids.domain.usecase.post

import com.sixkids.domain.repository.PostRepository
import javax.inject.Inject

class ReportPostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(postId: Long) = runCatching {
        postRepository.reportPost(postId)
    }
}