package com.sixkids.teacher.home.rank.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.theme.UlbanTypography
import com.sixkids.designsystem.R as UlbanRes

@Composable
fun RankItem(
    modifier: Modifier = Modifier,
    rank: Int = 0,
    name: String = "이름",
    exp: Int = 0,
) {
    val rankHeight = 40
    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        // Rank
        when (rank) {
            1, 2, 3 -> {
                Image(
                    modifier = Modifier
                        .height(rankHeight.dp)
                        .aspectRatio(1f)
                        .align(AbsoluteAlignment.CenterLeft),
                    painter = painterResource(
                        id = when (rank) {
                            1 -> UlbanRes.drawable.rank_first
                            2 -> UlbanRes.drawable.rank_second
                            3 -> UlbanRes.drawable.rank_third
                            else -> UlbanRes.drawable.rank_third
                        },
                    ),
                    contentDescription = null
                )
            }

            else -> {
                Box(
                    modifier = Modifier
                    .height(rankHeight.dp)
                    .align(Alignment.CenterStart),
                ){
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "${rank}등",
                        style = UlbanTypography.titleMedium,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
        // Name
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = name,
            style = UlbanTypography.titleMedium,
            maxLines = 1
        )
        // Exp
        Text(
            modifier = Modifier.align(Alignment.CenterEnd),
            text = "${exp}exp",
            style = UlbanTypography.titleMedium,
            maxLines = 1,

        )
    }
}

@Preview(showBackground = true)
@Composable
fun RankItemPreview() {
    RankItem(
        rank = 4
    )
}