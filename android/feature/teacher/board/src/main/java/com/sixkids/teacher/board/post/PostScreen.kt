package com.sixkids.teacher.board.post

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.component.appbar.UlbanDetailAppBar
import com.sixkids.designsystem.component.button.EditFAB
import com.sixkids.designsystem.theme.Blue
import com.sixkids.designsystem.theme.BlueDark
import com.sixkids.teacher.board.R
import com.sixkids.designsystem.R as UlbanRes

@Composable
fun PostRoute(
    padding: PaddingValues
) {
    Box(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
    ) {
        PostScreen()
    }
}


@Composable
fun PostScreen(
    modifier: Modifier = Modifier,
    fabClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Column(

        ) {
            UlbanDetailAppBar(
                leftIcon = UlbanRes.drawable.board,
                title = stringResource(id = R.string.board_main_post),
                content = stringResource(id = R.string.board_main_post),
                topDescription = "",
                bottomDescription = "",
                color = Blue
            )
        }
        LazyColumn {

        }
        //FAB
        EditFAB(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            buttonColor = Blue,
            iconColor = BlueDark,
            onClick = fabClick
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PostRoutePreview() {
    PostRoute(padding = PaddingValues(0.dp))
}