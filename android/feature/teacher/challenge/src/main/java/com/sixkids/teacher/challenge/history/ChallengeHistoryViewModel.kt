package com.sixkids.teacher.challenge.history

import com.sixkids.model.Challenge
import com.sixkids.ui.base.BaseViewModel

class ChallengeHistoryViewModel : BaseViewModel<ChallengeHistoryState, ChallengeHistoryEffect>(
    ChallengeHistoryState()
) {
    fun navigateChallengeDetail(challengeId: Int) = postSideEffect(
        ChallengeHistoryEffect.NavigateToChallengeDetail(challengeId)
    )

    init {
        intent {
            copy(
                challengeHistory = listOf(
                    Challenge(
                        id = 1,
                        title = "Challenge 1",
                        content = "Content 1",
                        headCount = 10
                    ),
                    Challenge(
                        id = 2,
                        title = "Challenge 2",
                        content = "Content 2",
                        headCount = 20
                    ),
                    Challenge(
                        id = 3,
                        title = "Challenge 3",
                        content = "Content 3",
                        headCount = 30
                    ),
                    Challenge(
                        id = 4,
                        title = "Challenge 4",
                        content = "Content 4",
                        headCount = 40
                    ),
                    Challenge(
                        id = 5,
                        title = "Challenge 5",
                        content = "Content 5",
                        headCount = 50
                    ),
                )
            )
        }
    }

}
