package com.sixkids.teacher.manageclass.chattingfilter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
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
import com.sixkids.designsystem.theme.Yellow
import com.sixkids.designsystem.theme.YellowDark
import com.sixkids.teacher.manageclass.R
import com.sixkids.designsystem.R as UlbanRes

@Composable
fun ChattingFilterRoute(

) {

}

@Composable
fun ChattingFilterScreen(
    modifier: Modifier = Modifier,
    fabClick: () -> Unit = {}
) {
    val listState = rememberLazyListState()

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column {
            UlbanDetailAppBar(
                leftIcon = UlbanRes.drawable.chat_filter,
                title = stringResource(id = R.string.manage_class_filter_chat),
                content = stringResource(id = R.string.manage_class_filter_chat),
                topDescription = "",
                bottomDescription = "",
                color = Yellow
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                state = listState,
            ) {

            }
        }
        //FAB
        EditFAB(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            buttonColor = Yellow,
            iconColor = YellowDark,
            onClick = fabClick
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChattingFilterScreenPreview() {
    ChattingFilterScreen()
}