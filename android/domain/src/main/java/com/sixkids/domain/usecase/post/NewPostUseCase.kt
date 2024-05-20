package com.sixkids.domain.usecase.post

import com.sixkids.domain.repository.PostRepository
import com.sixkids.model.Post
import java.io.File
import javax.inject.Inject

class NewPostUseCase @Inject constructor(
    private val postRepository: PostRepository
){
    suspend operator fun invoke(
        organizationId: Long,
        title: String,
        content: String,
        secretStatus: Boolean,
        postCategory: String,
        file: File?
    ) = runCatching {
        postRepository.createPost(
            organizationId,
            title,
            content,
            secretStatus,
            postCategory,
            file
        )
    }
}