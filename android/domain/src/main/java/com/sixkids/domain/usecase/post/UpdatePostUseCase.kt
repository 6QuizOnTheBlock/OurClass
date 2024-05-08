package com.sixkids.domain.usecase.post

import com.sixkids.domain.repository.PostRepository
import java.io.File
import javax.inject.Inject

class UpdatePostUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(
        postId: Long,
        title: String,
        content: String,
        secretStatus: Boolean,
        postCategory: String,
        file: File?
    ) = postRepository.updatePost(
        postId,
        title,
        content,
        secretStatus,
        postCategory,
        file
    )
}