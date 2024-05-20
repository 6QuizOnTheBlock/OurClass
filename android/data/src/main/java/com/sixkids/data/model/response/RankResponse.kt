package com.sixkids.data.model.response

import com.sixkids.model.MemberRankItem

data class RankResponse(
    val name: String,
    val point: Int,
)

fun List<RankResponse>.toModel(): List<MemberRankItem> {
    return this.mapIndexed { index, rankResponse ->
        MemberRankItem(
            rank = index + 1,
            name = rankResponse.name,
            exp = rankResponse.point,
        )
    }
}