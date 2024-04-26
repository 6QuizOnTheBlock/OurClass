package com.sixkids.designsystem.component.appbar

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.R
import com.sixkids.designsystem.theme.Orange
import com.sixkids.designsystem.theme.UlbanTheme

@Composable
fun ResponsiveTopBarWithLazyColumn() {
    val listState = rememberLazyListState()
    val isScrolled =
        listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 100

    Column(modifier = Modifier.fillMaxSize()) {
        UlbanDefaultAppBar(
            modifier = Modifier
                .fillMaxWidth(),
            leftIcon = {
                Icon(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 16.dp)
                        .aspectRatio(1f),
                    painter = painterResource(id = R.drawable.hifive),
                    contentDescription = "로고",
                    tint = Color.Unspecified
                )
            },
            title = "이어 달리기",
            content = "새로운\n이어 달리기\n만들기",
            color = Orange,
            onclick = { Log.d("확인", "클릭된 ") },
            expanded = !isScrolled
        )

        LazyColumn(
            state = listState, modifier = Modifier
                .weight(1f)
        ) {
            items(100) { index ->
                Text(
                    "Item $index", modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun ResponsiveTopBarWithLazyColumnPreview() {
    UlbanTheme {
        ResponsiveTopBarWithLazyColumn()
    }
}
