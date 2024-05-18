package com.sixkids.model

data class ClassSummary(
    val challengeCounts: List<MemberSimpleClassSummary>,
    val relayCounts: List<MemberSimpleClassSummary>,
    val postsCounts: List<MemberSimpleClassSummary>,
)