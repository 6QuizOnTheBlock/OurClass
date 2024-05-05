package com.sixkids.domain.usecase.challenge

import com.sixkids.domain.repository.ChallengeRepository
import com.sixkids.model.GroupSimple
import java.time.LocalDateTime
import javax.inject.Inject

class CreateChallengeUseCase @Inject constructor(
    private val challengeRepository: ChallengeRepository
) {
    suspend operator fun invoke(
        organizationId: Int,
        title: String,
        content: String,
        startTime: LocalDateTime,
        endTime: LocalDateTime,
        reword: Int,
        minCount: Int,
        groups: List<GroupSimple>?,
    ) = runCatching {
        challengeRepository.createChallenge(
            organizationId = organizationId,
            title = title,
            content = content,
            startTime = startTime,
            endTime = endTime,
            reword = reword,
            minCount = minCount,
            groups = groups
        )
    }
}
