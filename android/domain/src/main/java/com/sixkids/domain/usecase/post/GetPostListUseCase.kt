package com.sixkids.domain.usecase.post

import com.sixkids.domain.repository.PostRepository
import javax.inject.Inject

class GetPostListUseCase @Inject constructor(
    private val postRepository: PostRepository
) {
    suspend operator fun invoke(
        organizationId: Int,
        memberId: Int? = null,
        postCategory: String
    ) = postRepository.getPosts(
        organizationId,
        memberId,
        postCategory
    )
}