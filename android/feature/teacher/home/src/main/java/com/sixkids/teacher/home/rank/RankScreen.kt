package com.sixkids.teacher.home.rank

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RankRoute(
    padding: PaddingValues
) {

    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        RankScreen()
    }

}

@Composable
fun RankScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp, top = 20.dp),
    ) {
        Text(text = "Rank Screen")
    }
}


@Preview(showBackground = true)
@Composable
fun RankScreenPreview() {
    RankScreen()
}