package com.sixkids.teacher.challenge.create.matching

import com.sixkids.model.ChallengeGroup


data class GroupMatchingSuccessState(
    val isLoading: Boolean = false,
    val groupList: List<ChallengeGroup> = emptyList()
)