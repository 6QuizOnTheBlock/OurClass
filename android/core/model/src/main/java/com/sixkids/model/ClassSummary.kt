package com.sixkids.model

data class ClassSummary(
    val challengeCounts: List<MemberSimpleClassSummary> = emptyList(),
    val relayCounts: List<MemberSimpleClassSummary> = emptyList(),
    val postsCounts: List<MemberSimpleClassSummary> = emptyList(),
)