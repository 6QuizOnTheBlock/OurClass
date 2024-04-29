package com.sixkids.designsystem.component.appbar

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sixkids.designsystem.R
import com.sixkids.designsystem.theme.Orange
import com.sixkids.designsystem.theme.UlbanTheme

@Composable
fun RelayDefaultAppBarPreview() {
    val listState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 100
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        UlbanDefaultAppBar(
            modifier = Modifier
                .fillMaxWidth(),
            leftIcon = R.drawable.relay,
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

@Composable
fun RelayDetailAppBarPreview() {
    val listState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 100
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        UlbanDetailAppBar(
            modifier = Modifier
                .fillMaxWidth(),
            leftIcon = R.drawable.relay,
            title = "이어 달리기",
            content = "이어 달리기가\n진행 중입니다!",
            topDescription = "04.17 15:00~",
            bottomDescription = "현재 주자는 오하빈 학생입니다.",
            color = Orange,
            badgeCount = 10,
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


@Composable
fun RelayDetailWithProgressAppBarPreview() {
    val listState = rememberLazyListState()
    val isScrolled by remember {
        derivedStateOf {
            listState.firstVisibleItemIndex > 0 || listState.firstVisibleItemScrollOffset > 100
        }
    }
    Column(modifier = Modifier.fillMaxSize()) {
        UlbanDetailWithProgressAppBar(
            modifier = Modifier
                .fillMaxWidth(),
            leftIcon = R.drawable.relay,
            title = "이어 달리기",
            content = "이어 달리기가\n진행 중입니다!",
            topDescription = "04.17 15:00~",
            bottomDescription = "현재 주자는 오하빈 학생입니다.",
            totalCnt = 20,
            successCnt = 10,
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
fun DefaultAppBarPreview() {
    UlbanTheme {
        RelayDefaultAppBarPreview()
    }
}

@Preview(showBackground = true)
@Composable
fun DetailAppBarPreview() {
    UlbanTheme {
        RelayDetailAppBarPreview()
    }
}

@Preview(showBackground = true)
@Composable
fun DetailWithProgressAppBarPreview() {
    UlbanTheme {
        RelayDetailWithProgressAppBarPreview()
    }
}
